package io.annot8.defaultimpl.annotations;

import io.annot8.common.annotations.AbstractGroup;
import io.annot8.common.properties.EmptyImmutableProperties;
import io.annot8.common.stores.SaveCallback;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.annotations.Group;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.properties.MutableProperties;
import io.annot8.core.properties.Properties;
import io.annot8.core.references.AnnotationReference;
import io.annot8.defaultimpl.properties.SimpleImmutableProperties;
import io.annot8.defaultimpl.properties.SimpleMutableProperties;
import io.annot8.defaultimpl.references.SimpleAnnotationReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple implementation of Group interface
 */
public class SimpleGroup extends AbstractGroup {

  private final Item item;
  private final String id;
  private final String type;
  private final ImmutableProperties properties;

  // TODO: Better stored as (or as well as) a Guava Multimap ?
  private final Map<SimpleAnnotationReference, String> annotations;

  private SimpleGroup(final Item item, final String id, final String type,
      final ImmutableProperties properties,
      final Map<SimpleAnnotationReference, String> annotations) {
    this.item = item;
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
  public Map<String, Stream<AnnotationReference>> getReferences() {
    Map<String, Stream<AnnotationReference>> ret = new HashMap<>();

    annotations.forEach((key, value) -> {
      Stream<AnnotationReference> s = ret.getOrDefault(value, Stream.empty());
      ret.put(value, Stream.concat(s, Stream.of(key)));
    });

    return ret;
  }

  @Override
  public Optional<String> getRole(Annotation annotation) {
    return Optional.ofNullable(annotations.get(SimpleAnnotationReference.to(item, annotation)));
  }

  @Override
  public boolean containsAnnotation(Annotation annotation) {
    return annotations.containsKey(SimpleAnnotationReference.to(item, annotation));
  }

  /**
   * Builder class for SimpleGroup, using UUID for generating new IDs and
   * InMemoryImmutableProperties or EmptyImmutableProperties for the properties.
   */
  public static class Builder implements Group.Builder {

    private final Item item;
    private final SaveCallback<Group, Group> saver;

    private String id = null;
    private String type = null;
    private MutableProperties properties = new SimpleMutableProperties();
    private Map<Annotation, String> annotations = new HashMap<>();

    public Builder(Item item, SaveCallback<Group, Group> saver) {
      this.item = item;
      this.saver = saver;
    }

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
    public Group.Builder withoutProperty(String key, Object value) {
      Optional<Object> val = properties.get(key);
      if (val.isPresent() && val.get().equals(value))
        properties.remove(key);

      return this;
    }

    @Override
    public Group.Builder withoutProperty(String key) {
      properties.remove(key);
      return this;
    }

    @Override
    public io.annot8.core.annotations.Group.Builder withProperties(Properties properties) {
      this.properties = new SimpleMutableProperties(properties);
      return this;
    }

    @Override
    public io.annot8.core.annotations.Group.Builder newId() {
      this.id = null;
      return this;
    }

    @Override
    public io.annot8.core.annotations.Group.Builder from(Group from) {
      this.id = from.getId();
      this.type = from.getType();
      this.properties = new SimpleMutableProperties(from.getProperties());

      this.annotations = new HashMap<>();
      from.getAnnotations()
          .forEach((key, value) -> value.forEach(a -> this.annotations.put(a, key)));

      return this;
    }

    @Override
    public Group save() throws IncompleteException {

      if (id == null) {
        id = UUID.randomUUID().toString();
      }

      if (type == null) {
        throw new IncompleteException("Type has not been set");
      }

      ImmutableProperties immutableProperties;
      if (properties.getAll().isEmpty()) {
        immutableProperties = EmptyImmutableProperties.getInstance();
      } else {
        immutableProperties = new SimpleImmutableProperties.Builder().from(properties).save();
      }

      Map<SimpleAnnotationReference, String> references = annotations.entrySet().stream()
          .collect(Collectors.toMap(
              e -> SimpleAnnotationReference.to(item, e.getKey()),
              Entry::getValue
          ));

      Group group = new SimpleGroup(item, id, type, immutableProperties, references);
      return saver.save(group);
    }

    @Override
    public io.annot8.core.annotations.Group.Builder withAnnotation(String role,
        Annotation annotation) {
      annotations.put(annotation, role);

      return this;
    }

  }
}
