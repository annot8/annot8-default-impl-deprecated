package io.annot8.defaultimpl.factories;

import io.annot8.common.implementations.registries.ContentBuilderFactoryRegistry;
import io.annot8.core.data.Item;
import io.annot8.core.data.ItemFactory;
import io.annot8.defaultimpl.data.DefaultItem;
import java.util.Objects;

public class DefaultItemFactory implements ItemFactory {

  private final ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;

  public DefaultItemFactory(ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
  }

  @Override
  public Item create() {
    return new DefaultItem(this, contentBuilderFactoryRegistry);
  }

  @Override
  public Item create(Item parent) {
    Objects.requireNonNull(parent);
    return new DefaultItem(this, parent.getId(), contentBuilderFactoryRegistry);
  }

}
