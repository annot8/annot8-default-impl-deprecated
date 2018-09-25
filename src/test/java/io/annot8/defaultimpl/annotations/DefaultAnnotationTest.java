/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.defaultimpl.annotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.annot8.common.implementations.properties.MapMutableProperties;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.bounds.Bounds;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.annotations.DefaultAnnotation.Builder;
import io.annot8.testing.tck.impl.WithIdBuilderTestUtils;
import io.annot8.testing.tck.impl.WithPropertiesBuilderTestUtils;
import io.annot8.testing.testimpl.TestBounds;
import io.annot8.testing.testimpl.TestConstants;

public class DefaultAnnotationTest {

  private final Bounds bounds = new TestBounds();
  private SaveCallback<Annotation, Annotation> annotationSaver;

  @BeforeEach
  public void beforeEach() {
    annotationSaver = mock(SaveCallback.class);
    when(annotationSaver.save(any(Annotation.class))).then(a -> a.getArguments()[0]);
  }

  @Test
  public void testIncompleteBuilderNothingSet() {
    assertThrows(
        IncompleteException.class, new Builder(TestConstants.CONTENT_ID, annotationSaver)::save);
  }

  @Test
  public void testIncompleteBuilderNoBounds() {
    assertThrows(
        IncompleteException.class,
        new Builder(TestConstants.CONTENT_ID, annotationSaver).withType("TEST")::save);
  }

  @Test
  public void testIncompleteBuilderNoType() {
    assertThrows(
        IncompleteException.class,
        new Builder(TestConstants.CONTENT_ID, annotationSaver).withBounds(bounds)::save);
  }

  @Test
  public void testIncompleteBuilderNoContent() {
    assertThrows(
        IncompleteException.class,
        () -> new Builder(null, annotationSaver).withType("Test").withBounds(bounds).save());
  }

  @Test
  public void testMinimal() throws IncompleteException {
    Annotation a1 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID, annotationSaver)
            .withType("TEST")
            .withBounds(bounds)
            .save();
    assertNotNull(a1.getId());
    assertEquals("TEST", a1.getType());
    assertEquals(bounds, a1.getBounds());
    assertEquals(TestConstants.CONTENT_ID, a1.getContentId());
    assertTrue(a1.getProperties().getAll().isEmpty());

    verify(annotationSaver, only()).save(a1);
  }

  @Test
  public void testWithProperty() throws IncompleteException {
    Annotation a2 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID, annotationSaver)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .withProperty("key1", 17)
            .withProperty("key2", false)
            .save();
    assertNotNull(a2.getId());
    assertEquals(TestConstants.ANNOTATION_TYPE, a2.getType());
    assertEquals(bounds, a2.getBounds());
    assertEquals(TestConstants.CONTENT_ID, a2.getContentId());

    verify(annotationSaver, only()).save(a2);
  }

  @Test
  public void testWithProperties() throws IncompleteException {
    Map<String, Object> properties2 = new HashMap<>();
    properties2.put("key1", 17);
    properties2.put("key2", false);

    Annotation a3 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID, annotationSaver)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .withProperties(new MapMutableProperties(properties2))
            .save();
    Map<String, Object> properties3 = a3.getProperties().getAll();
    assertEquals(2, properties3.size());
    assertEquals(17, properties3.get("key1"));
    assertEquals(false, properties3.get("key2"));

    verify(annotationSaver, only()).save(a3);
  }

  @Test
  public void testFromExisting() throws IncompleteException {
    Annotation a1 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID, annotationSaver)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .save();

    clearInvocations(annotationSaver);

    Annotation a2 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID, annotationSaver)
            .from(a1)
            .withProperty("key1", 17)
            .withProperty("key2", false)
            .save();
    assertNotNull(a2.getId());
    assertEquals(a1.getId(), a2.getId());

    assertEquals(TestConstants.ANNOTATION_TYPE, a2.getType());
    assertEquals(bounds, a2.getBounds());
    assertEquals(TestConstants.CONTENT_ID, a2.getContentId());

    Map<String, Object> properties2 = a2.getProperties().getAll();
    assertEquals(2, properties2.size());
    assertEquals(17, properties2.get("key1"));
    assertEquals(false, properties2.get("key2"));

    verify(annotationSaver, only()).save(a2);
  }

  @Test
  public void testFromExistingWithNewId() throws IncompleteException {
    Annotation a1 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID, annotationSaver)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .save();

    clearInvocations(annotationSaver);

    Annotation a2 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID, annotationSaver)
            .from(a1)
            .newId()
            .save();
    assertNotNull(a2.getId());
    assertNotEquals(a1.getId(), a2.getId());

    verify(annotationSaver, only()).save(a2);
  }

  @Test
  public void testFromExistingOverridden() throws IncompleteException {
    Annotation a1 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_NAME, annotationSaver)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .save();

    clearInvocations(annotationSaver);

    Bounds otherBounds = new TestBounds();
    Annotation a2 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_NAME, annotationSaver)
            .from(a1)
            .withType("TEST2")
            .withBounds(otherBounds)
            .save();

    assertEquals(a1.getId(), a2.getId());
    assertEquals(a2.getType(), "TEST2");
    assertEquals(a2.getBounds(), otherBounds);

    // Check that a1 is unchecked
    assertEquals(a1.getType(), TestConstants.ANNOTATION_TYPE);
    assertEquals(a1.getBounds(), bounds);

    verify(annotationSaver, only()).save(a2);
  }

  @Test
  public void testProperties() {
    WithPropertiesBuilderTestUtils utils = new WithPropertiesBuilderTestUtils();
    Annotation.Builder builder =
        new Builder(TestConstants.CONTENT_ID, annotationSaver).withType("TEST").withBounds(bounds);
    utils.testWithPropertiesBuilder(builder);
  }

  @Test
  public void testWithIdBuilder() {
    WithIdBuilderTestUtils utils = new WithIdBuilderTestUtils();
    Annotation.Builder builder =
        new Builder(TestConstants.CONTENT_ID, annotationSaver).withType("TEST").withBounds(bounds);
    utils.testWithIdBuilder(builder);
  }
}
