package io.annot8.defaultimpl.factories;

import io.annot8.common.implementations.registries.ContentBuilderFactoryRegistry;
import io.annot8.core.data.Item;
import io.annot8.core.data.ItemFactory;
import io.annot8.defaultimpl.data.DefaultItem;
import java.util.Objects;
import java.util.function.Consumer;

public class DefaultItemFactory implements ItemFactory {

  private final ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;
  private final Consumer<Item> onCreateConsumer;

  public DefaultItemFactory(ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this(contentBuilderFactoryRegistry, null);
  }

  public DefaultItemFactory(ContentBuilderFactoryRegistry contentBuilderFactoryRegistry,
      Consumer<Item> onCreateConsumer) {
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
    this.onCreateConsumer = onCreateConsumer;
  }

  @Override
  public Item create() {
    return consume(new DefaultItem(this, contentBuilderFactoryRegistry));
  }

  @Override
  public Item create(Item parent) {
    Objects.requireNonNull(parent);
    return consume(new DefaultItem(this, parent.getId(), contentBuilderFactoryRegistry));
  }

  protected Item consume(Item item) {
    if (onCreateConsumer != null) {
      onCreateConsumer.accept(item);
    }
    return item;
  }
}
