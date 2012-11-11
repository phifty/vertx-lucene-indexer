package me.phifty.index;

import java.util.Set;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public interface Document {

  public Set<String> keys();

  public void putString(String fieldName, String value);

  public void putNumber(String fieldName, Number value);

  public String getString(String fieldName);

  public Number getNumber(String fieldName);

}
