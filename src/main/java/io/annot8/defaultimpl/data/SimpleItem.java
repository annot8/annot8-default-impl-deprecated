package io.annot8.defaultimpl.data;

import io.annot8.common.factories.ContentBuilderFactory;
import io.annot8.common.factories.ItemFactory;
import io.annot8.common.registries.ContentBuilderFactoryRegistry;
import io.annot8.common.utils.StreamUtils;
import io.annot8.core.data.Content;
import io.annot8.core.data.Content.Builder;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.UnsupportedContentException;
import io.annot8.core.properties.MutableProperties;
import io.annot8.core.stores.GroupStore;
import io.annot8.defaultimpl.properties.SimpleMutableProperties;
import io.annot8.defaultimpl.stores.SimpleGroupStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class SimpleItem implements Item {

  private final Map<String, Content<?>> contents = new HashMap<>();
  private final MutableProperties properties = new SimpleMutableProperties();
  private final ItemFactory itemFactory;
  private final ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;
  private final SimpleGroupStore groups;


  public SimpleItem(ItemFactory itemFactory, ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.itemFactory = itemFactory;
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
    this.groups = new SimpleGroupStore(this);
  }

  @Override
  public Stream<String> listContents() {
    return contents.keySet().stream();
  }

  @Override
  public Optional<Content<?>> getContent(String name) {
    return Optional.ofNullable(contents.get(name));
  }

  @Override
  public Stream<Content<?>> getContents() {
    return contents.values().stream();
  }

  @Override
  public <T extends Content<?>> Stream<T> getContents(Class<T> clazz) {
    return StreamUtils.cast(getContents(), clazz);
  }

  @Override
  public <C extends Content<D>, D> Builder<C, D> create(Class<C> clazz)
      throws UnsupportedContentException {
    Optional<ContentBuilderFactory<D, C>> factory = contentBuilderFactoryRegistry
        .get(clazz);

    if (!factory.isPresent()) {
      throw new UnsupportedContentException("Unknown content type: " + clazz.getSimpleName());
    }

    return factory.get().create(this, this::save);
  }

  private <D, C extends Content<D>> C save(C c) {
    assert c != null;
    contents.put(c.getName(), c);
    return c;
  }

  @Override
  public void removeContent(String name) {
    contents.remove(name);
  }

  @Override
  public Item createChildItem() {
    return itemFactory.create();
  }

  @Override
  public GroupStore getGroups() {
    return groups;
  }

  @Override
  public MutableProperties getProperties() {
    return properties;
  }

}
