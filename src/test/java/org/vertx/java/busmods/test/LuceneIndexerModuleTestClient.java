package org.vertx.java.busmods.test;

import org.vertx.java.busmods.LuceneIndexerModule;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.framework.TestClientBase;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class LuceneIndexerModuleTestClient extends TestClientBase {

  @Override
  public void start() {
    super.start();

    JsonObject configuration = new JsonObject();
    configuration.putString("base_address", "test.indexer");
    configuration.putString("storage", "memory");
    configuration.putNumber("max_results", 100);
    configuration.putString("default_field", "name");

    JsonObject fields = new JsonObject();
    fields.putString("id", "numeric");
    fields.putString("name", "text");
    fields.putString("email", "string");
    configuration.putObject("fields", fields);

    container.deployVerticle(LuceneIndexerModule.class.getName(), configuration, 1, new Handler<String>() {
      @Override
      public void handle(String deploymentId) {
        tu.appReady();
      }
    });
  }

  public void testAddDocument() {
    vertx.eventBus().send("test.indexer.add", generateTestDocument(), new Handler<Message<JsonObject>>() {
      @Override
      public void handle(Message<JsonObject> message) {
        try {
          tu.azzert(message.body.getBoolean("done"), "should add the test document");
        } finally {
          clearAllDocuments(new Handler<Boolean>() {
            @Override
            public void handle(Boolean done) {
              tu.testComplete();
            }
          });
        }
      }
    });
  }

  public void testUpdateDocument() {
    addTestDocument(new Handler<Boolean>() {
      @Override
      public void handle(Boolean done) {
        vertx.eventBus().send("test.indexer.update", generateUpdateMessage(), new Handler<Message<JsonObject>>() {
          @Override
          public void handle(Message<JsonObject> message) {
            try {
              tu.azzert(message.body.getBoolean("done"), "should update the test document");
            } finally {
              clearAllDocuments(new Handler<Boolean>() {
                @Override
                public void handle(Boolean done) {
                  tu.testComplete();
                }
              });
            }
          }
        });
      }
    });
  }

  public void testRemoveDocument() {
    addTestDocument(new Handler<Boolean>() {
      @Override
      public void handle(Boolean done) {
        vertx.eventBus().send("test.indexer.remove", generateQuery(), new Handler<Message<JsonObject>>() {
          @Override
          public void handle(Message<JsonObject> message) {
            fetchTestDocument(new Handler<JsonObject>() {
              @Override
              public void handle(JsonObject jsonObject) {
                try {
                  tu.azzert(jsonObject == null, "should remove the test document");
                } finally {
                  tu.testComplete();
                }
              }
            });
          }
        });
      }
    });
  }

  public void testSearch() {
    addTestDocument(new Handler<Boolean>() {
      @Override
      public void handle(Boolean done) {
        vertx.eventBus().send("test.indexer.search", generateQuery(), new Handler<Message<JsonObject>>() {
          @Override
          public void handle(Message<JsonObject> message) {
            JsonArray results = message.body.getArray("results");
            try {
              tu.azzert(results.size() > 0, "should return at least one result");
              tu.azzert(results.iterator().next().equals(generateTestDocument()), "should contain the test document");
            } finally {
              clearAllDocuments(new Handler<Boolean>() {
                @Override
                public void handle(Boolean aBoolean) {
                  tu.testComplete();
                }
              });
            }
          }
        });
      }
    });
  }

  public void testClear() {
    addTestDocument(new Handler<Boolean>() {
      @Override
      public void handle(Boolean aBoolean) {
        vertx.eventBus().send("test.indexer.clear", null, new Handler<Message<JsonObject>>() {
          @Override
          public void handle(Message<JsonObject> message) {
            try {
              tu.azzert(message.body.getBoolean("done"), "should clear all documents");
            } finally {
              tu.testComplete();
            }
          }
        });
      }
    });
  }

  private void addTestDocument(final Handler<Boolean> handler) {
    sendAndReceiveDoneMessage("test.indexer.add", generateTestDocument(), handler);
  }

  private void fetchTestDocument(final Handler<JsonObject> handler) {
    vertx.eventBus().send("test.indexer.search", generateQuery(), new Handler<Message<JsonObject>>() {
      @Override
      public void handle(Message<JsonObject> message) {
        JsonArray results = message.body.getArray("results");
        if (results.iterator().hasNext()) {
          handler.handle((JsonObject)results.iterator().next());
        } else {
          handler.handle(null);
        }
      }
    });
  }

  private void clearAllDocuments(final Handler<Boolean> handler) {
    sendAndReceiveDoneMessage("test.indexer.clear", null, handler);
  }

  private void sendAndReceiveDoneMessage(final String address, final JsonObject message, final Handler<Boolean> handler) {
    vertx.eventBus().send(address, message, new Handler<Message<JsonObject>>() {
      @Override
      public void handle(Message<JsonObject> message) {
        handler.handle(message.body.getBoolean("done"));
      }
    });
  }

  private JsonObject generateUpdateMessage() {
    JsonObject updatedDocument = generateUpdatedTestDocument();
    JsonObject message = new JsonObject();
    message.putString("field", "id");
    message.putString("value", updatedDocument.getNumber("id").toString());
    message.putObject("document", updatedDocument);
    return message;
  }

  private JsonObject generateTestDocument() {
    JsonObject document = new JsonObject();
    document.putNumber("id", 1);
    document.putString("name", "dummy");
    document.putString("email", "dummy@test.com");
    return document;
  }

  private JsonObject generateUpdatedTestDocument() {
    JsonObject document = generateTestDocument();
    document.putString("email", "dummi@test.com");
    return document;
  }

  private JsonObject generateQuery() {
    JsonObject query = new JsonObject();
    query.putString("query", "dum*");
    return query;
  }

}
