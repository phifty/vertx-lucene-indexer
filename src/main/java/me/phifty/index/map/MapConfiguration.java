package me.phifty.index.map;

import me.phifty.index.Configuration;
import me.phifty.index.DefaultConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class MapConfiguration implements Configuration {

  protected Configuration defaultConfiguration = new DefaultConfiguration();
  protected Map<String, Object> map;

  public MapConfiguration(Map<String, Object> map) {
    this.map = map;
  }

  @Override
  public Storage getStorage() {
    return map.containsKey("storage") ?
      Storage.valueOf(((String) map.get("storage")).toUpperCase()) :
      defaultConfiguration.getStorage();
  }

  @Override
  public String getPath() {
    return map.containsKey("path") ?
      (String)map.get("path") :
      defaultConfiguration.getPath();
  }

  @Override
  public Number getMaximalResults() {
    return map.containsKey("max_results") ?
      (Number)map.get("max_results") :
      defaultConfiguration.getMaximalResults();
  }

  @Override
  public String getDefaultFieldName() {
    return map.containsKey("default_field") ?
      (String)map.get("default_field") :
      defaultConfiguration.getDefaultFieldName();
  }

  @Override
  public Map<String, FieldType> getFields() {
    if (map.containsKey("fields")) {
      Map<String, Object> fields = (Map<String, Object>)map.get("fields");
      Map<String, FieldType> results = new HashMap<String, FieldType>();
      for (Map.Entry<String, Object> field : fields.entrySet()) {
        results.put(field.getKey(), FieldType.valueOf(((String)field.getValue()).toUpperCase()));
      }
      return results;
    } else {
      return defaultConfiguration.getFields();
    }
  }

}
