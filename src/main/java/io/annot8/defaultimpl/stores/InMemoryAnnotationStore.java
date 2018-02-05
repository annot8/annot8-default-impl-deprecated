package io.annot8.defaultimpl.stores;

import io.annot8.core.annotations.Annotation;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.annotations.SimpleAnnotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * In memory implementation, backed by a HashMap, of AnnotationStore
 */
public class InMemoryAnnotationStore implements AnnotationStore {

  private final Map<String, Annotation> annotations = new HashMap<>();
  private final Class<? extends Annotation.Builder> annotationBuilderClass;

  /**
   * Construct a new instance of this class using SimpleAnnotation.Builder as the annotation
   * builder
   */
  public InMemoryAnnotationStore() {
    annotationBuilderClass = SimpleAnnotation.Builder.class;
  }

  /**
   * Construct a new instance of this class using the specified Annotation.Builder as the annotation
   * builder
   */
  public InMemoryAnnotationStore(Class<? extends Annotation.Builder> annotationBuilderClass) {
    this.annotationBuilderClass = annotationBuilderClass;
  }

  @Override
  public Annotation.Builder getBuilder() {
    try {
      return annotationBuilderClass.getConstructor().newInstance();
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      //TODO: Log error
      return new SimpleAnnotation.Builder();
    }
  }

  @Override
  public Annotation save(Annotation.Builder builder) throws IncompleteException {
    Annotation a = builder.build();

    annotations.put(a.getId(), a);

    return a;
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
}
