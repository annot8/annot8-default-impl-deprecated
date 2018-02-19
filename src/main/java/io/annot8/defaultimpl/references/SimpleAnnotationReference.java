package io.annot8.defaultimpl.references;

import io.annot8.common.references.AbstractAnnotationReference;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import java.util.Optional;

/**
 * A reference which will always retrieve the latest annotation from the appropriate annotation
 * store.
 *
 * Does not hold a reference to the group.
 */
public class SimpleAnnotationReference extends AbstractAnnotationReference {

  private final Item item;

  private final String contentName;

  private final String annotationId;

  /**
   * New reference either from another reference or manually created.
   */
  public SimpleAnnotationReference(Item item, String contentName, String annotationId) {
    this.item = item;
    this.contentName = contentName;
    this.annotationId = annotationId;
  }

  /**
   * Create an annotation reference for the annotation.
   */
  public static SimpleAnnotationReference to(Item item, Annotation annotation) {
    return new SimpleAnnotationReference(item, annotation.getContentName(), annotation.getId());
  }

  @Override
  public String getAnnotationId() {
    return annotationId;
  }

  @Override
  public String getContentName() {
    return contentName;
  }

  @Override
  public Optional<Annotation> toAnnotation() {
    return item.getContent(contentName)
        .map(Content::getAnnotations)
        .flatMap(store -> store.getById(annotationId));
  }

}
