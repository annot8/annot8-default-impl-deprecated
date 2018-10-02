package io.annot8.defaultimpl.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import io.annot8.common.data.content.URLContent;
import io.annot8.common.implementations.stores.NoOpSaveCallback;
import io.annot8.core.data.Content.Builder;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.content.DefaultURL.BuilderFactory;
import io.annot8.testing.testimpl.TestItem;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;

public class DefaultURLTest {

  private static final String URL = "https://www.test.co.uk";

  @Test
  public void testDefaultURLBuilderFactory(){
    BuilderFactory factory = new DefaultURL.BuilderFactory();
    Builder<URLContent, URL> defaultURLBuilder = factory
        .create(new TestItem(), new NoOpSaveCallback<>());
    assertNotNull(defaultURLBuilder);
  }

  @Test
  public void testDefaultURLBuilder(){
    BuilderFactory builderFactory = new BuilderFactory();
    Builder<URLContent, URL> urlContentBuilder = builderFactory
        .create(new TestItem(), new NoOpSaveCallback<>());

    String id = "id";
    String name = "test";
    String key = "testKey";
    String prop = "testValue";
    URLContent content = null;
    try {
      content = urlContentBuilder
          .withId(id)
          .withName(name)
          .withData(new URL(URL))
          .withProperty(key, prop)
          .save();
    } catch (MalformedURLException | IncompleteException e) {
      fail("Test should not fail here", e);
    }

    assertNotNull(content);
    assertEquals(name, content.getName());
    assertEquals(URL, content.getData().toExternalForm());
    assertEquals(URL.class, content.getDataClass());
    assertEquals(URLContent.class, content.getContentClass());
    assertEquals(id, content.getId());
    assertTrue(content.getProperties().has(key));
    assertEquals(prop, content.getProperties().get(key).get());
    assertNotNull(content.getAnnotations());
  }

  @Test
  public void testDefaultURLBuilderFillsArgs(){
    BuilderFactory builderFactory = new BuilderFactory();
    Builder<URLContent, URL> urlContentBuilder = builderFactory
        .create(new TestItem(), new NoOpSaveCallback<>());

    URLContent content = null;
    try {
      content = urlContentBuilder.withName("test").withData(new URL(URL)).save();
    } catch (MalformedURLException | IncompleteException e) {
      fail("Test should not fail here", e);
    }

    assertNotNull(content.getId());
    assertNotNull(content.getProperties());
    assertTrue(content.getProperties().getAll().isEmpty());
  }

}
