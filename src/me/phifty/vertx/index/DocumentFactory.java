package me.phifty.vertx.index;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public interface DocumentFactory<T extends Document> {

  public T newDocument();

}
