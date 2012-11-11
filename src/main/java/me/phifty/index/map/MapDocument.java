package me.phifty.index.map;

import me.phifty.index.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class MapDocument implements Document {

  protected Map<String, Object> map;

  public MapDocument() {
    this.map = new HashMap<>();
  }

  public MapDocument(Map<String, Object> map) {
    this.map = map;
  }

  @Override
  public Set<String> keys() {
    return map.keySet();
  }

  @Override
  public void putString(String fieldName, String value) {
    map.put(fieldName, value);
  }

  @Override
  public void putNumber(String fieldName, Number value) {
    map.put(fieldName, value);
  }

  @Override
  public String getString(String fieldName) {
    return (String)map.get(fieldName);
  }

  @Override
  public Number getNumber(String fieldName) {
    return (Number)map.get(fieldName);
  }

}
