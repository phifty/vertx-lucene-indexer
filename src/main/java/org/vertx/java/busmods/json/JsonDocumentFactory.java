package org.vertx.java.busmods.json;

import me.phifty.index.DocumentFactory;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class JsonDocumentFactory implements DocumentFactory<JsonDocument> {

  @Override
  public JsonDocument newDocument() {
    return new JsonDocument();
  }

}
