package io.annot8.defaultimpl.content;

import io.annot8.common.data.content.Text;
import io.annot8.common.implementations.content.AbstractContent;
import io.annot8.common.implementations.content.AbstractContentBuilder;
import io.annot8.common.implementations.content.AbstractContentBuilderFactory;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.defaultimpl.stores.SimpleAnnotationStore;
import java.util.function.Supplier;

public class SimpleText extends AbstractContent<String> implements Text {

  private SimpleText(String id,  String name,
      ImmutableProperties properties, Supplier<String> data) {
    super(String.class, Text.class, new SimpleAnnotationStore(id), id, name, properties, data);
  }

  public static class Builder extends AbstractContentBuilder<String, SimpleText> {

    public Builder(SaveCallback<SimpleText, SimpleText> saver) {
      super( saver);
    }

    @Override
    public SimpleText create(String id, String name,
        ImmutableProperties properties, Supplier<String> data) {
      return new SimpleText(id, name, properties, data);
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
