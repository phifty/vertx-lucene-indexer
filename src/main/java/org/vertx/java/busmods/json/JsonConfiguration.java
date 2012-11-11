package org.vertx.java.busmods.json;

import me.phifty.index.map.MapConfiguration;
import org.vertx.java.busmods.Configuration;
import org.vertx.java.busmods.DefaultConfiguration;
import org.vertx.java.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class JsonConfiguration extends MapConfiguration implements Configuration {

  protected Configuration defaultConfiguration;

  public JsonConfiguration(JsonObject object) {
    super(object.toMap());
    this.defaultConfiguration = new DefaultConfiguration();
  }

  @Override
  public String getBaseAddress() {
    return map.containsKey("base_address") ?
      (String)map.get("base_address") :
      defaultConfiguration.getBaseAddress();
  }

}
