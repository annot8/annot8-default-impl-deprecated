package io.annot8.defaultimpl.stores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import io.annot8.core.annotations.Annotation;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.bounds.NoBounds;
import org.junit.jupiter.api.Test;

public class SimpleAnnotationStoreTest {
  @Test
  public void testInMemoryAnnotationStore() throws IncompleteException{
    AnnotationStore store = new SimpleAnnotationStore();

    assertEquals(0, store.getAll().count());

    Annotation a = store.save(store.getBuilder()
        .withBounds(NoBounds.getInstance())
        .withContent("TEST_CONTENT")
        .withType("TEST"));

    assertEquals(1, store.getAll().count());
    store.getAll().forEach(annot -> assertEquals(a, annot));
    assertEquals(a, store.getById(a.getId()).get());
    assertFalse(store.getById("TEST").isPresent());

    store.delete(a);
    assertEquals(0, store.getAll().count());
  }
}
