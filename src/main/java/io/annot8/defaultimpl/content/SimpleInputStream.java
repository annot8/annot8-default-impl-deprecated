package io.annot8.defaultimpl.content;

import io.annot8.common.data.content.InputStreamContent;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.data.AbstractContent;
import io.annot8.defaultimpl.data.AbstractContentBuilderFactory;
import java.io.InputStream;
import java.util.function.Supplier;

public class SimpleInputStream extends AbstractContent<InputStream> implements InputStreamContent {

  private final Supplier<InputStream> dataProvider;

  private SimpleInputStream(String id, AnnotationStore annotations, String name,
      ImmutableProperties properties, Supplier<InputStream> dataProvider) {
    super(id, annotations, name, properties);
    this.dataProvider = dataProvider;
  }

  @Override
  public InputStream getData() {
    return dataProvider.get();
  }

  @Override
  public Class<InputStream> getDataClass() {
    return InputStream.class;
  }

  @Override
  public Class<? extends Content<InputStream>> getContentClass() {
    return InputStreamContent.class;
  }

  public static class Builder extends AbstractContent.Builder<InputStream, SimpleInputStream> {

    private Supplier<InputStream> dataSupplier;

    public Builder(SaveCallback<SimpleInputStream, SimpleInputStream> saver) {
      super(saver);
    }

    @Override
    public Content.Builder<SimpleInputStream, InputStream> withData(Supplier<InputStream> dataSupplier) {
      this.dataSupplier = dataSupplier;
      return this;
    }

    @Override
    protected SimpleInputStream create(String id, AnnotationStore annotations, String name,
        ImmutableProperties properties) throws IncompleteException {

      if(dataSupplier == null) {
        throw new IncompleteException("No data provider");
      }

      return new SimpleInputStream(id, annotations, name, properties, dataSupplier);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<InputStream, SimpleInputStream> {

    public BuilderFactory() {
      super(InputStream.class, SimpleInputStream.class);
    }

    @Override
    public Content.Builder<SimpleInputStream, InputStream> create(Item item,
        SaveCallback<SimpleInputStream, SimpleInputStream> saver) {
      return new SimpleInputStream.Builder(saver);
    }

  }
}
