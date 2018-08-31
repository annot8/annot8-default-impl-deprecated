package io.annot8.defaultimpl.content;

import io.annot8.common.data.content.FileContent;
import io.annot8.common.implementations.content.AbstractContent;
import io.annot8.common.implementations.content.AbstractContentBuilder;
import io.annot8.common.implementations.content.AbstractContentBuilderFactory;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.defaultimpl.stores.SimpleAnnotationStore;
import java.io.File;
import java.util.function.Supplier;

public class SimpleFile extends AbstractContent<File> implements FileContent {

  private SimpleFile(String id, String name,
      ImmutableProperties properties, Supplier<File> data) {
    super(File.class, FileContent.class, new SimpleAnnotationStore(id), id, name, properties, data);
  }

  public static class Builder extends AbstractContentBuilder<File, SimpleFile> {

    public Builder(SaveCallback<SimpleFile, SimpleFile> saver) {
      super(saver);
    }

    @Override
    protected SimpleFile create(String id, String name,
        ImmutableProperties properties,  Supplier<File> data) {
      return new SimpleFile(id, name, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<File, SimpleFile> {

    public BuilderFactory() {
      super(File.class, SimpleFile.class);
    }

    @Override
    public Content.Builder<SimpleFile, File> create(Item item,
        SaveCallback<SimpleFile, SimpleFile> saver) {
      return new SimpleFile.Builder(saver);
    }

  }

}

