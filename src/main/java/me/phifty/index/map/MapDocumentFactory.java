package me.phifty.index.map;

import me.phifty.index.DocumentFactory;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class MapDocumentFactory implements DocumentFactory<MapDocument> {

  @Override
  public MapDocument newDocument() {
    return new MapDocument();
  }

}
