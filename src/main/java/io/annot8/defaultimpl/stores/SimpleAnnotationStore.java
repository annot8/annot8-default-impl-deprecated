package io.annot8.defaultimpl.stores;

import io.annot8.common.implementations.factories.AnnotationBuilderFactory;
import io.annot8.common.implementations.stores.AnnotationStoreFactory;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.data.Content;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.annotations.SimpleAnnotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In memory implementation, backed by a HashMap, of AnnotationStore
 */
public class SimpleAnnotationStore implements AnnotationStore {

  private final Map<String, Annotation> annotations = new ConcurrentHashMap<>();
  private final String contentId;
  private final AnnotationBuilderFactory<Annotation> annotationBuilderFactory;

  /**
   * Construct a new instance of this class using SimpleAnnotation.AbstractContentBuilder as the annotation
   * builder
   */
  public SimpleAnnotationStore(String contentId) {
    this.contentId = contentId;
    this.annotationBuilderFactory = (content, store, saver) -> new SimpleAnnotation.Builder(
        contentId, this::save);
  }

  /**
   * Construct a new instance of this class using a custom annotation builder.
   */
  public SimpleAnnotationStore(String contentId,
      AnnotationBuilderFactory<Annotation> annotationBuilderFactory) {
    this.contentId = contentId;
    this.annotationBuilderFactory = annotationBuilderFactory;
  }


  @Override
  public Annotation.Builder getBuilder() {
    return annotationBuilderFactory.create(contentId, this, this::save);
  }

  private Annotation save(Annotation annotation) {
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



  public static AnnotationStoreFactory factory() {
    return SimpleAnnotationStoreFactory.INSTANCE;
  }

  public static class SimpleAnnotationStoreFactory implements AnnotationStoreFactory {

    private static final SimpleAnnotationStoreFactory INSTANCE = new SimpleAnnotationStoreFactory();

    @Override
    public AnnotationStore create(Content<?> content) {
      return new SimpleAnnotationStore(content.getId());
    }
  }
}
