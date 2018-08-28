package io.annot8.defaultimpl.references;

import io.annot8.common.implementations.references.AbstractAnnotationReference;
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

  private final String contentId;

  private final String annotationId;

  /**
   * New reference either from another reference or manually created.
   */
  public SimpleAnnotationReference(Item item, String contentId, String annotationId) {
    this.item = item;
    this.contentId = contentId;
    this.annotationId = annotationId;
  }

  /**
   * Create an annotation reference for the annotation.
   */
  public static SimpleAnnotationReference to(Item item, Annotation annotation) {
    return new SimpleAnnotationReference(item, annotation.getContentId(), annotation.getId());
  }

  @Override
  public String getAnnotationId() {
    return annotationId;
  }

  @Override
  public String getContentId() {
    return contentId;
  }

  @Override
  public Optional<Annotation> toAnnotation() {
    return item.getContent(contentId)
        .map(Content::getAnnotations)
        .flatMap(store -> store.getById(annotationId));
  }

}
