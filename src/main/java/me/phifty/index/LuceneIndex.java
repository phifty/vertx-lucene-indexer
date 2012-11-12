package me.phifty.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.AbstractField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author phifty <b.phifty@gmail.com>
 */
public class LuceneIndex<T extends Document> implements Index<T> {

  private IndexConfiguration indexConfiguration;
  private DocumentFactory<T> documentFactory;

  private static RAMDirectory ramDirectory = new RAMDirectory();

  private Directory directory;
  private IndexWriter writer;
  private IndexReader reader;
  private IndexSearcher searcher;
  private QueryParser queryParser;

  public LuceneIndex(IndexConfiguration indexConfiguration, DocumentFactory<T> documentFactory) throws IOException {
    this.indexConfiguration = indexConfiguration;
    this.documentFactory = documentFactory;
    initializeDirectory();
    initializeIndexWriter();
  }

  @Override
  public void add(T document) throws IOException {
    writer.addDocument(convert(document));
    writer.commit();
  }

  @Override
  public void update(String key, String value, T document) throws IOException {
    writer.updateDocument(new Term(key, value), convert(document));
    writer.commit();
  }

  @Override
  public void remove(String queryText) throws IOException, IndexException {
    try {
      Query query = queryParser.parse(queryText);
      writer.deleteDocuments(query);
      writer.commit();
    } catch (ParseException exception) {
      throw(new IndexException(exception.toString()));
    }
  }

  @Override
  public void clear() throws IOException {
    writer.deleteAll();
    writer.commit();
  }

  @Override
  public List<T> search(String queryText) throws IOException, IndexException {
    updateReader();
    try {
      Query query = queryParser.parse(queryText);

      TopDocs results = searcher.search(query, indexConfiguration.getMaximalResults().intValue());
      ScoreDoc[] hits = results.scoreDocs;

      ArrayList<T> documents = new ArrayList<T>();
      for (ScoreDoc hit : hits) {
        documents.add(convert(searcher.doc(hit.doc)));
      }
      return documents;
    } catch (ParseException exception) {
      throw(new IndexException(exception.toString()));
    }
  }

  @Override
  public void shutdown() throws IOException {
    writer.close();
    if (reader != null) {
      reader.close();
    }
  }

  private void initializeDirectory() throws IOException {
    switch (indexConfiguration.getStorage()) {
      case MEMORY:
        directory = ramDirectory;
        break;
      case FILESYSTEM:
        directory = FSDirectory.open(new File(indexConfiguration.getPath()));
        break;
    }
  }

  private void initializeIndexWriter() throws IOException {
    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);

    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_31, analyzer);
    indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
    writer = new IndexWriter(directory, indexWriterConfig);

    queryParser = new QueryParser(Version.LUCENE_31, indexConfiguration.getDefaultFieldName(), analyzer);
  }

  private void updateReader() throws IOException {
    if (reader == null) {
      reader = IndexReader.open(directory);
      searcher = new IndexSearcher(reader);
    } else {
      IndexReader newReader = IndexReader.openIfChanged(reader);
      if (newReader != null) {
        reader.close();
        reader = newReader;
        searcher = new IndexSearcher(reader);
      }
    }
  }

  private org.apache.lucene.document.Document convert(T document) {
    org.apache.lucene.document.Document result = new org.apache.lucene.document.Document();

    for (String key : document.keys()) {
      result.add(buildField(document, key));
    }

    return result;
  }

  private T convert(org.apache.lucene.document.Document document) {
    T result = documentFactory.newDocument();

    for (Fieldable field : document.getFields()) {
      IndexConfiguration.FieldType fieldType = indexConfiguration.getFields().get(field.name());

      switch (fieldType) {
        case NUMERIC:
          result.putNumber(field.name(), Long.parseLong(field.stringValue()));
          break;
        case STRING:
        case TEXT:
        default:
          result.putString(field.name(), field.stringValue());
          break;
      }
    }

    return result;
  }

  private AbstractField buildField(T document, String key) {
    IndexConfiguration.FieldType fieldType = indexConfiguration.getFields().get(key);

    switch (fieldType) {
      case NUMERIC:
        NumericField numericField = new NumericField(key, Field.Store.YES, true);
        numericField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY);
        numericField.setLongValue(document.getNumber(key).longValue());
        return numericField;
      case TEXT:
        Field textField = new Field(key, document.getString(key), Field.Store.YES, Field.Index.ANALYZED);
        textField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY);
        return textField;
      case STRING:
      default:
        Field stringField = new Field(key, document.getString(key), Field.Store.YES, Field.Index.NOT_ANALYZED);
        stringField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY);
        return stringField;
    }
  }

}
