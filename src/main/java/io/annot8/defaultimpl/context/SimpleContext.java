package io.annot8.defaultimpl.context;

import io.annot8.core.components.Resource;
import io.annot8.core.context.Context;
import io.annot8.core.settings.Settings;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Simple implementation of Context, backed by a HashMap to store resources
 */
public class SimpleContext implements Context{

  private final Map<String, Resource> resources = new HashMap<>();
  private final Settings settings;

  /**
   * Create a new instance, with no settings and no resources
   */
  public SimpleContext(){
    this.settings = null;
  }

  /**
   * Create a new instance, with the specified settings and no resources
   */
  public SimpleContext(Settings settings){
    this.settings = settings;
  }

  /**
   * Create a new instance, with no settings and the specified resources
   */
  public SimpleContext(Map<String, Resource> resources){
    this.settings = null;
    this.resources.putAll(resources);
  }

  /**
   * Create a new instance, with the specified settings and resources
   */
  public SimpleContext(Settings settings, Map<String, Resource> resources){
    this.settings = settings;
    this.resources.putAll(resources);
  }

  /**
   * Add a new resource to the context object
   */
  public void addResource(String key, Resource resource){
    resources.put(key, resource);
  }

  @Override
  public Optional<Settings> getSettings() {
    return Optional.ofNullable(settings);
  }

  @Override
  public <T extends Resource> Optional<T> getResource(String s, Class<T> aClass) {
    Resource r = resources.get(s);

    if(r == null || !aClass.isAssignableFrom(r.getClass())) {
      return Optional.empty();
    }else{
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
        .map(r -> aClass.cast(r));
  }
}
