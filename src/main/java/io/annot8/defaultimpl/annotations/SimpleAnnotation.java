package io.annot8.defaultimpl.annotations;

import java.util.Objects;
import java.util.UUID;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.bounds.Bounds;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.properties.MutableProperties;
import io.annot8.core.properties.Properties;
import io.annot8.common.properties.EmptyImmutableProperties;
import io.annot8.defaultimpl.properties.SimpleImmutableProperties;
import io.annot8.defaultimpl.properties.SimpleMutableProperties;

/**
 * Simple implementation of Annotation interface
 */
public class SimpleAnnotation implements Annotation {
  
  private final String id;
  private final String type;
  private final ImmutableProperties properties;
  private final Bounds bounds;
  private final String content;
  
  private SimpleAnnotation(final String id, final String type, final ImmutableProperties properties, final Bounds bounds, final String content) {
    this.id = id;
    this.type = type;
    this.properties = properties;
    this.bounds = bounds;
    this.content = content;
  }
  
  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }

  @Override
  public Bounds getBounds() {
    return bounds;
  }

  @Override
  public String getContentName() {
    return content;
  }
  
  @Override
  public String toString() {
    return this.getClass().getName() + " [id=" + id + ", type=" + type + ", contentName=" + content + ", bounds=" + bounds + ", properties="+properties +"]";
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, type, properties, bounds, content);
  }
  
  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Annotation))
      return false;
    
    Annotation a = (Annotation) o;
    
    return Objects.equals(id, a.getId()) && Objects.equals(type, a.getType())
        && Objects.equals(properties, a.getProperties()) && Objects.equals(bounds, a.getBounds())
        && Objects.equals(content, a.getContentName());
  }

  /**
   * Builder class for SimpleAnnotation, using UUID for generating new IDs
   * and InMemoryImmutableProperties or EmptyImmutableProperties for the
   * properties. 
   */
  public static class Builder implements Annotation.Builder{
    private String id = UUID.randomUUID().toString();
    private String type = null;
    private MutableProperties properties = new SimpleMutableProperties();
    private Bounds bounds = null;
    private final String content;

    public Builder(String content) {
        this.content = content;
    }

    @Override
    public io.annot8.core.annotations.Annotation.Builder withType(String type) {
      this.type = type;
      return this;
    }

    @Override
    public io.annot8.core.annotations.Annotation.Builder withProperty(String key, Object value) {
      properties.set(key, value);
      return this;
    }

    @Override
    public io.annot8.core.annotations.Annotation.Builder withProperties(Properties properties) {
      this.properties = new SimpleMutableProperties(properties);
      return this;
    }

    @Override
    public io.annot8.core.annotations.Annotation.Builder newId() {
      this.id = UUID.randomUUID().toString();
      return this;
    }

    @Override
    public io.annot8.core.annotations.Annotation.Builder from(Annotation from) {
      this.id = from.getId();
      this.type = from.getType();
      this.properties = new SimpleMutableProperties(from.getProperties());
      this.bounds = from.getBounds();

      return this;
    }

    @Override
    public Annotation save() throws IncompleteException {
      if(id == null)
        throw new IncompleteException("ID has not been set");
      
      if(type == null)
        throw new IncompleteException("Type has not been set");
      
      if(bounds == null)
        throw new IncompleteException("Bounds has not been set");
      
      if(content == null)
        throw new IncompleteException("Content name has not been set");
      
      ImmutableProperties immutableProperties;
      if(properties.getAll().isEmpty()) {
        immutableProperties = EmptyImmutableProperties.getInstance();
      }else {
        immutableProperties = new SimpleImmutableProperties.Builder().from(properties).save();
      }
      
      return new SimpleAnnotation(id, type, immutableProperties, bounds, content);
    }

    @Override
    public io.annot8.core.annotations.Annotation.Builder withBounds(Bounds bounds) {
      this.bounds = bounds;
      return this;
    }
    
  }
}
