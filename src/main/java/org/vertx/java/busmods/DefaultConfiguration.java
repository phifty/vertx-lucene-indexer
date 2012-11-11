package org.vertx.java.busmods;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class DefaultConfiguration extends me.phifty.index.DefaultConfiguration implements Configuration {

  @Override
  public String getBaseAddress() {
    return "indexer";
  }

}
