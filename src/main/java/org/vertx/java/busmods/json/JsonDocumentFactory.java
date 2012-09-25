package org.vertx.java.busmods.json;

import org.vertx.java.busmods.DocumentFactory;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class JsonDocumentFactory implements DocumentFactory<JsonDocument> {

  @Override
  public JsonDocument newDocument() {
    return new JsonDocument();
  }

}
