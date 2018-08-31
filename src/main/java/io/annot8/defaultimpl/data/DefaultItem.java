package io.annot8.defaultimpl.data;


import io.annot8.common.implementations.factories.ContentBuilderFactory;
import io.annot8.common.implementations.registries.ContentBuilderFactoryRegistry;
import io.annot8.core.data.Content;
import io.annot8.core.data.Content.Builder;
import io.annot8.core.data.Item;
import io.annot8.core.data.ItemFactory;
import io.annot8.core.exceptions.UnsupportedContentException;
import io.annot8.core.properties.MutableProperties;
import io.annot8.core.stores.GroupStore;
import io.annot8.common.implementations.properties.MapMutableProperties;
import io.annot8.defaultimpl.stores.DefaultGroupStore;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class DefaultItem implements Item {

  private final Map<String, Content<?>> contents = new ConcurrentHashMap<>();
  private final MutableProperties properties = new MapMutableProperties();
  private final ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;
  private final DefaultGroupStore groups;
  private final String id;
  private final ItemFactory itemFactory;
  private final String parentId;
  private boolean discarded = false;

  public DefaultItem(ItemFactory itemFactory, String parentId, ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.itemFactory = itemFactory;
    this.parentId = parentId;
    this.id = UUID.randomUUID().toString();
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
    this.groups = new DefaultGroupStore(this);
  }

  public DefaultItem(ItemFactory itemFactory, ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this(itemFactory, null, contentBuilderFactoryRegistry);
  }

  public Optional<String> getParent() {
    return Optional.ofNullable(parentId);
  }

  @Override
  public Optional<Content<?>> getContent(String id) {
    return Optional.ofNullable(contents.get(id));
  }

  @Override
  public Stream<Content<?>> getContents() {
    return contents.values().stream();
  }

  @Override
  public <C extends Content<D>, D> Builder<C, D> create(Class<C> clazz)
      throws UnsupportedContentException {
    Optional<ContentBuilderFactory<D, C>> factory = contentBuilderFactoryRegistry.get(clazz);

    if (!factory.isPresent()) {
      throw new UnsupportedContentException("Unknown content type: " + clazz.getSimpleName());
    }

    return factory.get().create(this, this::save);
  }

  private <D, C extends Content<D>> C save(C c) {
    assert c != null;
    contents.put(c.getId(), c);
    return c;
  }

  @Override
  public void removeContent(String name) {
    contents.remove(name);
  }

  @Override
  public Item createChildItem() {
    return itemFactory.create(this);
  }

  @Override
  public GroupStore getGroups() {
    return groups;
  }

  @Override
  public MutableProperties getProperties() {
    return properties;
  }

  @Override
  public void discard() {
    discarded = true;
  }

  @Override
  public boolean isDiscarded() {
    return discarded;
  }

  @Override
  public String getId() {
    return id;
  }
}
