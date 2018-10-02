/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.defaultimpl.content;

import java.net.URL;
import java.util.function.Supplier;

import io.annot8.common.data.content.URLContent;
import io.annot8.common.implementations.content.AbstractContentBuilder;
import io.annot8.common.implementations.content.AbstractContentBuilderFactory;
import io.annot8.common.implementations.stores.AnnotationStoreFactory;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.stores.DefaultAnnotationStore;

public class DefaultURL implements URLContent {

  private final String id;
  private final String name;
  private final URL url;
  private final ImmutableProperties properties;
  private final AnnotationStore store;

  private DefaultURL(String id, String name, URL url, ImmutableProperties properties) {
    this.id = id;
    this.name = name;
    this.url = url;
    this.properties = properties;
    this.store = new DefaultAnnotationStore(id);
  }

  @Override
  public URL getData() {
    return url;
  }

  @Override
  public Class<URL> getDataClass() {
    return URL.class;
  }

  @Override
  public Class<? extends Content<URL>> getContentClass() {
    return URLContent.class;
  }

  @Override
  public AnnotationStore getAnnotations() {
    return store;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }

  public static class Builder extends AbstractContentBuilder<URL, URLContent> {

    private SaveCallback<URLContent, URLContent> callback;
    private AnnotationStoreFactory factory;

    public Builder(SaveCallback<URLContent, URLContent> saver) {
      super(saver);
      this.callback = saver;
    }

    @Override
    protected URLContent create(
        String id, String name, ImmutableProperties properties, Supplier<URL> data) {
      return new DefaultURL(id, name, data.get(), properties);
    }
  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<URL, URLContent> {

    public BuilderFactory() {
      super(URL.class, URLContent.class);
    }

    @Override
    public Content.Builder<URLContent, URL> create(
        Item item, SaveCallback<URLContent, URLContent> saver) {
      return new Builder(saver);
    }
  }
}
