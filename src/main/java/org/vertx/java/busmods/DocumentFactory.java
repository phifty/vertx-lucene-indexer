package org.vertx.java.busmods;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public interface DocumentFactory<T extends Document> {

  public T newDocument();

}
