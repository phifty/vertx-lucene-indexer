package org.vertx.java.busmods.json;

import me.phifty.index.Document;
import org.vertx.java.core.json.JsonObject;

import java.util.Set;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class JsonDocument implements Document {

  private JsonObject object;

  public JsonDocument() {
    this.object = new JsonObject();
  }

  public JsonDocument(JsonObject object) {
    this.object = object;
  }

  public JsonObject getObject() {
    return object;
  }

  @Override
  public Set<String> keys() {
    return object.getFieldNames();
  }

  @Override
  public void putString(String key, String value) {
    object.putString(key, value);
  }

  @Override
  public void putNumber(String fieldName, Number value) {
    object.putNumber(fieldName, value);
  }

  @Override
  public String getString(String key) {
    return object.getString(key);
  }

  @Override
  public Number getNumber(String fieldName) {
    return object.getNumber(fieldName);
  }

}
