package org.vertx.java.busmods;

import me.phifty.index.FieldType;

import java.util.Map;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public interface Configuration {

  enum Storage {
    MEMORY, FILESYSTEM
  }

  public String getBaseAddress();

  public Storage getStorage();

  public String getPath();

  public Number getMaximalResults();

  public String getDefaultFieldName();

  public Map<String, FieldType> getFields();

}
