package org.vertx.java.busmods;

import java.util.Map;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public interface Configuration {

  enum Storage {
    MEMORY, FILESYSTEM
  }

  enum FieldType {
    NUMERIC, STRING, TEXT
  }

  public String getBaseAddress();

  public Storage getStorage();

  public String getPath();

  public Number getMaximalResults();

  public String getDefaultFieldName();

  public Map<String, FieldType> getFields();

}
