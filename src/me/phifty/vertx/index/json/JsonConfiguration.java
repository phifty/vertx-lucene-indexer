package me.phifty.vertx.index.json;

import me.phifty.vertx.index.Configuration;
import me.phifty.vertx.index.DefaultConfiguration;
import org.vertx.java.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class JsonConfiguration implements Configuration {

  private JsonObject object;
  private Configuration defaultConfiguration;

  public JsonConfiguration(JsonObject object) {
    this.object = object;
    this.defaultConfiguration = new DefaultConfiguration();
  }

  @Override
  public String getBaseAddress() {
    return object.getString("base_address", defaultConfiguration.getBaseAddress());
  }

  @Override
  public Storage getStorage() {
    return object.getString("storage") == null ?
      defaultConfiguration.getStorage() :
      Storage.valueOf(object.getString("storage").toUpperCase());
  }

  @Override
  public String getPath() {
    return object.getString("path", defaultConfiguration.getPath());
  }

  @Override
  public Number getMaximalResults() {
    return object.getNumber("max_results", defaultConfiguration.getMaximalResults().intValue());
  }

  @Override
  public String getDefaultFieldName() {
    return object.getString("default_field", defaultConfiguration.getDefaultFieldName());
  }

  @Override
  public Map<String, FieldType> getFields() {
    JsonObject fields = object.getObject("fields");

    if (fields == null) {
      return defaultConfiguration.getFields();
    } else {
      Map<String, FieldType> results = new HashMap<String, FieldType>();
      for (String fieldName : fields.getFieldNames()) {
        results.put(fieldName, FieldType.valueOf(fields.getString(fieldName).toUpperCase()));
      }
      return results;
    }
  }

}
