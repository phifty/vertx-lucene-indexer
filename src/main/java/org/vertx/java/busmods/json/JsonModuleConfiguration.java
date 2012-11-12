package org.vertx.java.busmods.json;

import org.vertx.java.busmods.DefaultModuleConfiguration;
import org.vertx.java.busmods.ModuleConfiguration;
import org.vertx.java.core.json.JsonObject;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class JsonModuleConfiguration implements ModuleConfiguration {

  private JsonObject object;
  private ModuleConfiguration defaultModuleConfiguration;

  public JsonModuleConfiguration(JsonObject object) {
    this.object = object;
    this.defaultModuleConfiguration = new DefaultModuleConfiguration();
  }

  @Override
  public String getBaseAddress() {
    return object.getString("base_address", defaultModuleConfiguration.getBaseAddress());
  }

}
