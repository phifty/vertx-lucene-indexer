package me.phifty.index;

import me.phifty.index.Document;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public interface DocumentFactory<T extends Document> {

  public T newDocument();

}
