package io.annot8.defaultimpl.stores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import io.annot8.common.data.bounds.NoBounds;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.test.TestConstants;
import org.junit.jupiter.api.Test;

public class SimpleAnnotationStoreTest {

  @Test
  public void testInMemoryAnnotationStore() throws IncompleteException {
    String contentName = TestConstants.CONTENT_NAME;
    AnnotationStore store = new SimpleAnnotationStore(contentName);

    assertEquals(0, store.getAll().count());

    Annotation a = store.getBuilder()
        .withBounds(NoBounds.getInstance())
        .withType("TEST")
        .save();

    assertEquals(1, store.getAll().count());
    store.getAll().forEach(annot -> assertEquals(a, annot));
    assertEquals(a, store.getById(a.getId()).get());
    assertFalse(store.getById("TEST").isPresent());

    store.delete(a);
    assertEquals(0, store.getAll().count());
  }
}
