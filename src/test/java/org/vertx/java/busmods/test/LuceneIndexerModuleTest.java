package org.vertx.java.busmods.test;

import org.junit.Test;
import org.vertx.java.framework.TestBase;

public class LuceneIndexerModuleTest extends TestBase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    startApp(LuceneIndexerModuleTestClient.class.getName());
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testAddDocument() {
    startTest(getMethodName());
  }

  @Test
  public void testUpdateDocument() {
    startTest(getMethodName());
  }

  @Test
  public void testRemoveDocument() {
    startTest(getMethodName());
  }

  @Test
  public void testSearch() {
    startTest(getMethodName());
  }

  @Test
  public void testClear() {
    startTest(getMethodName());
  }

}
