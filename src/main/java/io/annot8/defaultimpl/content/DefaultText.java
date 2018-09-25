/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.defaultimpl.content;

import java.util.function.Supplier;

import io.annot8.common.data.content.Text;
import io.annot8.common.implementations.content.AbstractContent;
import io.annot8.common.implementations.content.AbstractContentBuilder;
import io.annot8.common.implementations.content.AbstractContentBuilderFactory;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.defaultimpl.stores.DefaultAnnotationStore;

public class DefaultText extends AbstractContent<String> implements Text {

  private DefaultText(
      String id, String name, ImmutableProperties properties, Supplier<String> data) {
    super(String.class, Text.class, new DefaultAnnotationStore(id), id, name, properties, data);
  }

  public static class Builder extends AbstractContentBuilder<String, DefaultText> {

    public Builder(SaveCallback<DefaultText, DefaultText> saver) {
      super(saver);
    }

    @Override
    public DefaultText create(
        String id, String name, ImmutableProperties properties, Supplier<String> data) {
      return new DefaultText(id, name, properties, data);
    }
  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<String, DefaultText> {

    public BuilderFactory() {
      super(String.class, DefaultText.class);
    }

    @Override
    public Content.Builder<DefaultText, String> create(
        Item item, SaveCallback<DefaultText, DefaultText> saver) {
      return new DefaultText.Builder(saver);
    }
  }
}
