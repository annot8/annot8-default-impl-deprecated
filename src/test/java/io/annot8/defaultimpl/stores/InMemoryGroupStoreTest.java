package io.annot8.defaultimpl.stores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import io.annot8.core.annotations.Annotation;
import io.annot8.core.annotations.Group;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.core.stores.GroupStore;
import io.annot8.defaultimpl.bounds.NoBounds;
import org.junit.jupiter.api.Test;

public class InMemoryGroupStoreTest {
  @Test
  public void testInMemoryAnnotationStore() throws IncompleteException{
    GroupStore store = new InMemoryGroupStore();

    assertEquals(0, store.getAll().count());

    Annotation a1 = mock(Annotation.class);
    Annotation a2 = mock(Annotation.class);

    Group g = store.save(store.getBuilder()
        .withType("TEST")
        .withAnnotation("source", a1)
        .withAnnotation("target", a2));

    assertEquals(1, store.getAll().count());
    store.getAll().forEach(group -> assertEquals(g, group));
    assertEquals(g, store.getById(g.getId()).get());
    assertFalse(store.getById("TEST").isPresent());

    store.delete(g);
    assertEquals(0, store.getAll().count());
  }
}
