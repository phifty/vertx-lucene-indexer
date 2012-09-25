package org.vertx.java.busmods;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class DefaultConfiguration implements Configuration {

  @Override
  public String getBaseAddress() {
    return "indexer";
  }

  @Override
  public Storage getStorage() {
    return Storage.MEMORY;
  }

  @Override
  public String getPath() {
    return System.getProperty("user.home") + File.pathSeparator + "index";
  }

  @Override
  public Number getMaximalResults() {
    return 10;
  }

  @Override
  public String getDefaultFieldName() {
    return "id";
  }

  @Override
  public Map<String, FieldType> getFields() {
    return new HashMap<String, FieldType>();
  }

}
