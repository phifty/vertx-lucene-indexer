package org.vertx.java.busmods;

import java.io.IOException;
import java.util.List;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public interface Index<T extends Document> {

  public void add(T document) throws IOException;

  public void update(String key, String value, T Document) throws IOException;

  public void remove(String query) throws IOException, IndexException;

  public void clear() throws IOException;

  public List<T> search(String query) throws IOException, IndexException;

  public void shutdown() throws IOException;

}
