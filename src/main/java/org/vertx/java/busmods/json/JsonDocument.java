package org.vertx.java.busmods.json;

import me.phifty.index.Document;
import me.phifty.index.map.MapDocument;
import org.vertx.java.core.json.JsonObject;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class JsonDocument extends MapDocument implements Document {

  public JsonDocument() {
    super();
  }

  public JsonDocument(JsonObject object) {
    super(object.toMap());
  }

  public JsonObject getObject() {
    return new JsonObject(map);
  }

}
