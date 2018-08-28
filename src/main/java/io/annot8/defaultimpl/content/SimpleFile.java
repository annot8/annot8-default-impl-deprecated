package io.annot8.defaultimpl.content;

import io.annot8.common.data.content.FileContent;
import io.annot8.common.implementations.content.AbstractContent;
import io.annot8.common.implementations.content.AbstractContentBuilder;
import io.annot8.common.implementations.content.AbstractContentBuilderFactory;
import io.annot8.common.implementations.stores.AnnotationStoreFactory;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.properties.ImmutableProperties;
import java.io.File;
import java.util.function.Supplier;

public class SimpleFile extends AbstractContent<File> implements FileContent {

  private SimpleFile(AnnotationStoreFactory annotationStoreFactory, String id, String name,
      ImmutableProperties properties, Supplier<File> data) {
    super(File.class, FileContent.class, annotationStoreFactory, id, name, properties, data);
  }

  public static class Builder extends AbstractContentBuilder<File, SimpleFile> {

    public Builder(AnnotationStoreFactory annotationStoreFactory, SaveCallback<SimpleFile, SimpleFile> saver) {
      super(annotationStoreFactory, saver);
    }

    @Override
    protected SimpleFile create(String id, String name,
        ImmutableProperties properties,  Supplier<File> data) {
      return new SimpleFile(getAnnotationStoreFactory(), id, name, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<File, SimpleFile> {

    public BuilderFactory(AnnotationStoreFactory annotationStoreFactory) {
      super(File.class, SimpleFile.class, annotationStoreFactory);
    }

    @Override
    public Content.Builder<SimpleFile, File> create(Item item,
        SaveCallback<SimpleFile, SimpleFile> saver) {
      return new SimpleFile.Builder(getAnnotationStoreFactory(), saver);
    }

  }

}

