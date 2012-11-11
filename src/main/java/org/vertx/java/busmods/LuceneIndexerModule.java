package org.vertx.java.busmods;

import me.phifty.index.Index;
import me.phifty.index.LuceneIndex;
import org.vertx.java.busmods.json.JsonConfiguration;
import org.vertx.java.busmods.json.JsonDocument;
import org.vertx.java.busmods.json.JsonDocumentFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Verticle;

import java.util.List;

public class LuceneIndexerModule extends Verticle {

  private Configuration configuration;
  private Index index = null;

  @Override
  public void start() throws Exception {
    initializeConfiguration();
    initializeIndex();
    registerAddHandler();
    registerUpdateHandler();
    registerRemoveHandler();
    registerSearchHandler();
    registerClearHandler();
  }

  @Override
  public void stop() throws Exception {
    index.shutdown();
    super.stop();
  }

  private void initializeConfiguration() {
    configuration = new JsonConfiguration(container.getConfig());
  }

  private void initializeIndex() throws Exception {
    index = new LuceneIndex<JsonDocument>(configuration, new JsonDocumentFactory());
  }

  private void registerAddHandler() {
    final Index _index = index;
    vertx.eventBus().registerHandler(configuration.getBaseAddress() + ".add", new Handler<Message<JsonObject>>() {
      @Override
      public void handle(Message<JsonObject> message) {
        try {
          _index.add(new JsonDocument(message.body));
          message.reply(doneMessage());
        } catch (Exception exception) {
          exception.printStackTrace();
          message.reply(failMessage());
        }
      }
    });
  }

  private void registerUpdateHandler() {
    final Index _index = index;
    vertx.eventBus().registerHandler(configuration.getBaseAddress() + ".update", new Handler<Message<JsonObject>>() {
      @Override
      public void handle(Message<JsonObject> message) {
        try {
          _index.update(message.body.getString("field"), message.body.getString("value"), new JsonDocument(message.body.getObject("document")));
          message.reply(doneMessage());
        } catch (Exception exception) {
          exception.printStackTrace();
          message.reply(failMessage());
        }
      }
    });
  }

  private void registerRemoveHandler() {
    final Index _index = index;
    vertx.eventBus().registerHandler(configuration.getBaseAddress() + ".remove", new Handler<Message<JsonObject>>() {
      @Override
      public void handle(Message<JsonObject> message) {
        try {
          _index.remove(message.body.getString("query"));
          message.reply(doneMessage());
        } catch (Exception exception) {
          exception.printStackTrace();
          message.reply(failMessage());
        }
      }
    });
  }

  private void registerSearchHandler() {
    final Index _index = index;
    vertx.eventBus().registerHandler(configuration.getBaseAddress() + ".search", new Handler<Message<JsonObject>>() {
      @Override
      public void handle(Message<JsonObject> message) {
        try {
          List<JsonDocument> results = _index.search(message.body.getString("query"));
          message.reply(resultsMessage(results));
        } catch (Exception exception) {
          exception.printStackTrace();
          message.reply(failMessage());
        }
      }
    });
  }

  private void registerClearHandler() {
    final Index _index = index;
    vertx.eventBus().registerHandler(configuration.getBaseAddress() + ".clear", new Handler<Message<JsonObject>>() {
      @Override
      public void handle(Message<JsonObject> message) {
        try {
          _index.clear();
          message.reply(doneMessage());
        } catch (Exception exception) {
          exception.printStackTrace();
          message.reply(failMessage());
        }
      }
    });
  }

  private JsonObject doneMessage() {
    JsonObject message = new JsonObject();
    message.putBoolean("done", true);
    return message;
  }

  private JsonObject failMessage() {
    JsonObject message = new JsonObject();
    message.putBoolean("done", false);
    return message;
  }

  private JsonObject resultsMessage(List<JsonDocument> resultDocuments) {
    JsonObject message = new JsonObject();
    JsonArray results = new JsonArray();
    for (JsonDocument document : resultDocuments) {
      results.add(document.getObject());
    }
    message.putArray("results", results);
    return message;
  }

}
