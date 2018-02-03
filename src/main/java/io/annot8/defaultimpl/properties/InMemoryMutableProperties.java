package io.annot8.defaultimpl.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import io.annot8.core.properties.MutableProperties;
import io.annot8.core.properties.Properties;

/**
 * Implementation of MutableProperties interface using an in-memory
 * HashMap to store the properties.
 */
public class InMemoryMutableProperties implements MutableProperties {
  private Map<String, Object> properties = new HashMap<>();
  
  /**
   * Create a new instance with no key-values
   */
  public InMemoryMutableProperties() {
    //Do nothing
  }
  
  /**
   * Create a new instance with key-values from an existing
   * Properties object
   */
  public InMemoryMutableProperties(Properties properties) {
    properties.getAll().entrySet().forEach(e -> this.properties.put(e.getKey(), e.getValue()));
  }
  
  /**
   * Create a new instance with key-values from an existing Map
   */
  public InMemoryMutableProperties(Map<String, Object> properties) {
    properties.entrySet().forEach(e -> this.properties.put(e.getKey(), e.getValue()));
  }
  
  @Override
  public Map<String, Object> getAll() {
    return properties;
  }

  @Override
  public void set(String key, Object value) {
    properties.put(key, value);
  }

  @Override
  public Optional<Object> remove(String key) {
    if(properties.containsKey(key)) {
      return Optional.of(properties.remove(key));
    }else {
      return Optional.empty();
    }
  }

}
