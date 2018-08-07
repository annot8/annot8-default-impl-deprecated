package io.annot8.defaultimpl.content;

import java.io.File;
import io.annot8.common.content.FileContent;
import io.annot8.common.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.data.Tags;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.data.AbstractSimpleContent;

public class SimpleFile extends AbstractSimpleContent<File> implements FileContent {

  private SimpleFile(String id, AnnotationStore annotations, String name, Tags tags,
      ImmutableProperties properties, File data) {
    super(id, annotations, name, tags, properties, data);
  }

  @Override
  public Class<File> getDataClass() {
    return File.class;
  }

  @Override
  public Class<? extends Content<File>> getContentClass() {
    return FileContent.class;
  }

  public static class Builder extends AbstractSimpleContent.Builder<File, SimpleFile> {

    public Builder(SaveCallback<SimpleFile, SimpleFile> saver) {
      super(saver);
    }

    @Override
    protected SimpleFile create(String id, AnnotationStore annotations, String name, Tags tags,
        ImmutableProperties properties, File data) {
      return new SimpleFile(id, annotations, name, tags, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractSimpleContent.BuilderFactory<File, SimpleFile> {

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

