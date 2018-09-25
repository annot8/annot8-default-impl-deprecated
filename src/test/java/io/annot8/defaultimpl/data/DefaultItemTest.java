package io.annot8.defaultimpl.data;

import io.annot8.common.implementations.factories.SimpleItemFactory;
import io.annot8.core.data.Item;
import io.annot8.defaultimpl.factories.DefaultContentBuilderFactoryRegistry;
import io.annot8.defaultimpl.factories.DefaultItemCreator;
import io.annot8.testing.tck.impl.AbstractItemTest;

public class DefaultItemTest extends AbstractItemTest {

    @Override
    protected Item getItem() {
        DefaultContentBuilderFactoryRegistry registry = new DefaultContentBuilderFactoryRegistry(true);
        SimpleItemFactory itemFactory = new SimpleItemFactory(
            new DefaultItemCreator(registry));
        return new DefaultItem(itemFactory, registry);
    }
}