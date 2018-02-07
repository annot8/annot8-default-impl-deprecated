package io.annot8.defaultimpl.annotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.annotations.Group;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.properties.MutableProperties;
import io.annot8.core.properties.Properties;
import io.annot8.defaultimpl.properties.EmptyImmutableProperties;
import io.annot8.defaultimpl.properties.SimpleImmutableProperties;
import io.annot8.defaultimpl.properties.SimpleMutableProperties;

/**
 * Simple implementation of Group interface
 */
public class SimpleGroup implements Group{
  private final String id;
  private final String type;
  private final ImmutableProperties properties;
  private final Map<Annotation, String> annotations;
  
  private SimpleGroup(final String id, final String type, final ImmutableProperties properties, final Map<Annotation, String> annotations) {
    this.id = id;
    this.type = type;
    this.properties = properties;
    this.annotations = annotations;
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
  public Map<String, Stream<Annotation>> getAnnotations() {
    Map<String, Stream<Annotation>> ret = new HashMap<>();
        
    annotations.entrySet().forEach(e -> {
      Stream<Annotation> s = ret.getOrDefault(e.getValue(), Stream.empty());
      ret.put(e.getValue(), Stream.concat(s, Stream.of(e.getKey())));
    });
    
    return ret;
  }

  @Override
  public Optional<String> getRole(Annotation annotation) {
    return Optional.ofNullable(annotations.get(annotation));
  }

  @Override
  public boolean containsAnnotation(Annotation annotation) {
    return annotations.containsKey(annotation);
  }
  
  @Override
  public String toString() {
    return this.getClass().getName() + " [id=" + id + ", type=" + type + ", properties=" + properties + ", annotations=" + annotations + "]";
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, type, properties, annotations);
  }
  
  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Group))
      return false;
    
    Group g = (Group) o;
    
    Map<Annotation, String> m = new HashMap<>();
    g.getAnnotations().entrySet().forEach(e -> {
      e.getValue().forEach(a -> m.put(a, e.getKey()));
    });
    
    return Objects.equals(id, g.getId()) && Objects.equals(type, g.getType())
        && Objects.equals(properties, g.getProperties()) && Objects.equals(annotations, m);
  }
  
  /**
   * Builder class for SimpleGroup, using UUID for generating new IDs
   * and InMemoryImmutableProperties or EmptyImmutableProperties for the
   * properties. 
   */
  public static class Builder implements Group.Builder{
    private String id = UUID.randomUUID().toString();
    private String type = null;
    private MutableProperties properties = new SimpleMutableProperties();
    private Map<Annotation, String> annotations = new HashMap<>();
    
    @Override
    public io.annot8.core.annotations.Group.Builder withType(String type) {
      this.type = type;
      return this;
    }

    @Override
    public io.annot8.core.annotations.Group.Builder withProperty(String key, Object value) {
      properties.set(key, value);
      return this;
    }

    @Override
    public io.annot8.core.annotations.Group.Builder withProperties(Properties properties) {
      this.properties = new SimpleMutableProperties(properties);
      return this;
    }

    @Override
    public io.annot8.core.annotations.Group.Builder newId() {
      this.id = UUID.randomUUID().toString();
      return this;
    }

    @Override
    public io.annot8.core.annotations.Group.Builder from(Group from) {
      this.id = from.getId();
      this.type = from.getType();
      this.properties = new SimpleMutableProperties(from.getProperties());

      this.annotations = new HashMap<>();
      from.getAnnotations().entrySet().forEach(e -> {
        e.getValue().forEach(a -> this.annotations.put(a, e.getKey()));
      });
      
      return this;
    }

    @Override
    public Group save() throws IncompleteException {
      if(id == null)
        throw new IncompleteException("ID has not been set");
      
      if(type == null)
        throw new IncompleteException("Type has not been set");
      
      if(annotations.size() < 2)
        throw new IncompleteException("Groups must contain at least two annotations");
      
      ImmutableProperties immutableProperties;
      if(properties.getAll().isEmpty()) {
        immutableProperties = EmptyImmutableProperties.getInstance();
      }else {
        immutableProperties = new SimpleImmutableProperties.Builder().from(properties).save();
      }
      
      return new SimpleGroup(id, type, immutableProperties, annotations);
    }

    @Override
    public io.annot8.core.annotations.Group.Builder withAnnotation(String role,
        Annotation annotation) {
      annotations.put(annotation, role);

      return this;
    }
    
  }
}
