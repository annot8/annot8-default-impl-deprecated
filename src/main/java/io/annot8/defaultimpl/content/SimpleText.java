package io.annot8.defaultimpl.content;

import io.annot8.common.data.content.Text;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.data.AbstractContentBuilderFactory;
import io.annot8.defaultimpl.data.AbstractSimpleContent;

public class SimpleText extends AbstractSimpleContent<String> implements Text {

  private SimpleText(String id, AnnotationStore annotations, String name,
      ImmutableProperties properties, String data) {
    super(id, annotations, name, properties, data);
  }

  @Override
  public Class<? extends Content<String>> getContentClass() {
    return Text.class;
  }

  public static class Builder extends AbstractSimpleContent.Builder<String, SimpleText> {

    public Builder(SaveCallback<SimpleText, SimpleText> saver) {
      super(saver);
    }

    @Override
    protected SimpleText create(String id, AnnotationStore annotations, String name,
        ImmutableProperties properties, String data) {
      return new SimpleText(id, annotations, name, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<String, SimpleText> {

    public BuilderFactory() {
      super(String.class, SimpleText.class);
    }

    @Override
    public Content.Builder<SimpleText, String> create(Item item,
        SaveCallback<SimpleText, SimpleText> saver) {
      return new SimpleText.Builder(saver);
    }

  }

}
