package me.phifty.index;

import java.util.Map;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public interface IndexConfiguration {

  enum Storage {
    MEMORY, FILESYSTEM
  }

  enum FieldType {
    NUMERIC, STRING, TEXT
  }

  public Storage getStorage();

  public String getPath();

  public Number getMaximalResults();

  public String getDefaultFieldName();

  public Map<String, FieldType> getFields();

}
