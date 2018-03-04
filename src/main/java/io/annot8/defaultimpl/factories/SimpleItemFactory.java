package io.annot8.defaultimpl.factories;

import io.annot8.common.factories.ItemFactory;
import io.annot8.common.registries.ContentBuilderFactoryRegistry;
import io.annot8.core.data.Item;
import io.annot8.defaultimpl.data.SimpleItem;

public class SimpleItemFactory implements ItemFactory {

  private final ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;

  public SimpleItemFactory(ContentBuilderFactoryRegistry contentBuilderFactoryRegistry){
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
  }

  @Override
  public Item create() {
    return new SimpleItem(this, contentBuilderFactoryRegistry);
  }
}
