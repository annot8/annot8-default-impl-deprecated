package io.annot8.defaultimpl.stores;

import io.annot8.core.annotations.Annotation;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.annotations.SimpleAnnotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In memory implementation, backed by a HashMap, of AnnotationStore
 */
public class SimpleAnnotationStore implements AnnotationStore {

  private final Map<String, Annotation> annotations = new HashMap<>();
  private final String contentName;

  /**
   * Construct a new instance of this class using SimpleAnnotation.Builder as the annotation
   * builder
   */
  public SimpleAnnotationStore(String contentName) {
    this.contentName = contentName;
  }


  @Override
  public Annotation.Builder getBuilder() {
    return new SimpleAnnotation.Builder(contentName, this::save);
  }

  public Annotation save(Annotation annotation) {
    annotations.put(annotation.getId(), annotation);
    return annotation;
  }

  @Override
  public void delete(Annotation annotation) {
    annotations.remove(annotation.getId(), annotation);
  }

  @Override
  public Stream<Annotation> getAll() {
    return annotations.values().stream();
  }

  @Override
  public Optional<Annotation> getById(String s) {
    return Optional.ofNullable(annotations.get(s));
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [annotations=" + annotations.size() + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotations);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AnnotationStore)) {
      return false;
    }

    AnnotationStore as = (AnnotationStore) obj;

    Set<Annotation> allAnnotations = as.getAll().collect(Collectors.toSet());

    return Objects
        .equals(new HashSet<>(annotations.values()), allAnnotations);
  }
}
