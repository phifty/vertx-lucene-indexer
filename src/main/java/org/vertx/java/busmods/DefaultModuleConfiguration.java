package org.vertx.java.busmods;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class DefaultModuleConfiguration implements ModuleConfiguration {

  @Override
  public String getBaseAddress() {
    return "indexer";
  }

}
