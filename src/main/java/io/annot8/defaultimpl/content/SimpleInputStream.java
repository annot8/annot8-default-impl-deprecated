package io.annot8.defaultimpl.content;

import io.annot8.common.data.content.InputStreamContent;
import io.annot8.common.implementations.content.AbstractContent;
import io.annot8.common.implementations.content.AbstractContentBuilder;
import io.annot8.common.implementations.content.AbstractContentBuilderFactory;
import io.annot8.common.implementations.stores.AnnotationStoreFactory;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.Annot8RuntimeException;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import java.io.InputStream;
import java.util.function.Supplier;

public class SimpleInputStream extends AbstractContent<InputStream> implements InputStreamContent {


  private SimpleInputStream(AnnotationStoreFactory annotationStoreFactory, String id, String name,
      ImmutableProperties properties, Supplier<InputStream> data) {
    super(InputStream.class, InputStreamContent.class, annotationStoreFactory, id, name, properties, data);
  }

  public static class Builder extends AbstractContentBuilder<InputStream, SimpleInputStream> {

    public Builder(AnnotationStoreFactory annotationStoreFactory, SaveCallback<SimpleInputStream, SimpleInputStream> saver) {
      super(annotationStoreFactory, saver);
    }

    @Override
    public Content.Builder<SimpleInputStream, InputStream> withData(InputStream data) {
      throw new Annot8RuntimeException("Must use a Supplier to provider InputStream, otherwise it can only be read once");
    }

    @Override
    protected SimpleInputStream create(String id, String name,
        ImmutableProperties properties, Supplier<InputStream> data) throws IncompleteException {
      return new SimpleInputStream(getAnnotationStoreFactory(), id, name, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<InputStream, SimpleInputStream> {

    public BuilderFactory(AnnotationStoreFactory annotationStoreFactory) {
      super(InputStream.class, SimpleInputStream.class, annotationStoreFactory);
    }

    @Override
    public Content.Builder<SimpleInputStream, InputStream> create(Item item,
        SaveCallback<SimpleInputStream, SimpleInputStream> saver) {
      return new SimpleInputStream.Builder(getAnnotationStoreFactory(), saver);
    }

  }
}
