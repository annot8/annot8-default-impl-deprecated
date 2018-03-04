package io.annot8.defaultimpl.context;

import io.annot8.common.factories.ItemFactory;
import io.annot8.core.components.Resource;
import io.annot8.core.context.Context;
import io.annot8.core.settings.Settings;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Simple implementation of Context, backed by a HashMap to store resources
 */
public class SimpleContext implements Context {

  private final Map<String, Resource> resources = new HashMap<>();
  private final ItemFactory itemFactory;
  private final Settings settings;

  /**
   * Create a new instance, with no settings and no resources
   */
  public SimpleContext(ItemFactory itemFactory) {
    this(itemFactory, null, null);
  }

  /**
   * Create a new instance, with the specified settings and no resources
   */
  public SimpleContext(ItemFactory itemFactory, Settings settings) {
    this(itemFactory, settings, null);
  }

  /**
   * Create a new instance, with no settings and the specified resources
   */
  public SimpleContext(ItemFactory itemFactory, Map<String, Resource> resources) {
    this(itemFactory, null, resources);
  }

  /**
   * Create a new instance, with the specified settings and resources
   */
  public SimpleContext(ItemFactory itemFactory, Settings settings,
      Map<String, Resource> resources) {
    Objects.requireNonNull(itemFactory);

    this.itemFactory = itemFactory;
    this.settings = settings;
    if (resources != null) {
      this.resources.putAll(resources);
    }
  }

  /**
   * Add a new resource to the context object
   */
  public void addResource(String key, Resource resource) {
    resources.put(key, resource);
  }

  @Override
  public Optional<Settings> getSettings() {
    return Optional.ofNullable(settings);
  }

  @Override
  public <T extends Resource> Optional<T> getResource(String s, Class<T> aClass) {
    Resource r = resources.get(s);

    if (r == null || !aClass.isAssignableFrom(r.getClass())) {
      return Optional.empty();
    } else {
      return Optional.of(aClass.cast(r));
    }
  }

  @Override
  public Stream<String> getResourceKeys() {
    return resources.keySet().stream();
  }

  @Override
  public <T extends Resource> Stream<T> getResources(Class<T> aClass) {
    return resources.values().stream()
        .filter(r -> aClass.isAssignableFrom(r.getClass()))
        .map(aClass::cast);
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [settings=" + settings + ", resources=" + resources + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(settings, resources);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Context)) {
      return false;
    }

    Context c = (Context) obj;

    Map<String, Resource> resourceMap = new HashMap<>();
    c.getResourceKeys().forEach(s -> {
      Optional<Resource> r = c.getResource(s, Resource.class);
      r.ifPresent(resource -> resourceMap.put(s, resource));
    });

    return Objects.equals(c.getSettings(), getSettings()) && Objects
        .equals(resourceMap, this.resources);
  }

}
