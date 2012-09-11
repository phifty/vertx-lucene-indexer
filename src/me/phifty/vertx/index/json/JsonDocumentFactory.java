package me.phifty.vertx.index.json;

import me.phifty.vertx.index.DocumentFactory;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class JsonDocumentFactory implements DocumentFactory<JsonDocument> {

  @Override
  public JsonDocument newDocument() {
    return new JsonDocument();
  }

}
