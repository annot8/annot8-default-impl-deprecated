package io.annot8.defaultimpl.content;

import io.annot8.common.content.FileContent;
import io.annot8.common.stores.SaveFromBuilder;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.data.Tags;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.data.AbstractSimpleContent;
import java.io.File;

public class SimpleFile extends AbstractSimpleContent<File> implements FileContent {

  private SimpleFile(AnnotationStore annotations,
      String name, Tags tags,
      ImmutableProperties properties, File data) {
    super(annotations, name, tags, properties, data);
  }

  @Override
  public Class getDataClass() {
    return File.class;
  }

  public static class Builder extends AbstractSimpleContent.Builder<File, SimpleFile> {

    public Builder(SaveFromBuilder<SimpleFile, SimpleFile> saver) {
      super(saver);
    }

    @Override
    protected SimpleFile create(AnnotationStore annotations, String name, Tags tags,
        ImmutableProperties properties, File data) {
      return new SimpleFile(annotations, name, tags, properties, data);
    }
  }

  public static class BuilderFactory extends
      AbstractSimpleContent.BuilderFactory<File, SimpleFile> {

    public BuilderFactory() {
      super(File.class, SimpleFile.class);
    }

    @Override
    public Content.Builder<SimpleFile, File> create(Item item,
        SaveFromBuilder<SimpleFile, SimpleFile> saver) {
      return new SimpleFile.Builder(saver);
    }

  }

}

