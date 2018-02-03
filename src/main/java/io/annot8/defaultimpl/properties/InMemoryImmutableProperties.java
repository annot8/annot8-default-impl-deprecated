package io.annot8.defaultimpl.properties;

import java.util.HashMap;
import java.util.Map;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.properties.Properties;

/**
 * Implementation of ImmutableProperties interface using an in-memory
 * HashMap to store the properties.
 */
public class InMemoryImmutableProperties implements ImmutableProperties {

  private final Map<String, Object> properties;

  private InMemoryImmutableProperties(Map<String, Object> properties) {
    this.properties = properties;
  }

  @Override
  public Map<String, Object> getAll() {
    return properties;
  }

  /**
   * Builder class for InMemoryImmutableProperties
   */
  public static class Builder implements ImmutableProperties.Builder{

    private Map<String, Object> properties = new HashMap<>();

    @Override
    public io.annot8.core.properties.ImmutableProperties.Builder from(Properties from) {
      properties.clear();
      from.getAll().entrySet().forEach(e -> properties.put(e.getKey(), e.getValue()));
      return this;
    }

    @Override
    public io.annot8.core.properties.ImmutableProperties.Builder withProperty(String key,
        Object value) {
      properties.put(key, value);
      return this;
    }

    @Override
    public io.annot8.core.properties.ImmutableProperties.Builder withProperties(
        Properties properties) {
      properties.getAll().entrySet().forEach(e -> this.properties.put(e.getKey(), e.getValue()));
      return this;
    }

    @Override
    public ImmutableProperties build() throws IncompleteException {
      return new InMemoryImmutableProperties(properties);
    }

  }
}
