package io.annot8.defaultimpl.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Map;
import org.junit.jupiter.api.Test;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.bounds.NoBounds;
import io.annot8.defaultimpl.properties.EmptyImmutableProperties;
import io.annot8.defaultimpl.properties.InMemoryMutableProperties;

public class SimpleAnnotationTest {
  @Test
  public void testSimpleAnnotation() throws IncompleteException{
    //TODO: Can we use mocks to remove dependency on InMemoryMutableProperties and NoBounds?
    
    try {
      new SimpleAnnotation.Builder().build();
      fail("Expected exception not thrown");
    }catch(IncompleteException ie) {
      //Expected exception, do nothing
    }
    
    try {
      new SimpleAnnotation.Builder().withType("TEST").build();
      fail("Expected exception not thrown");
    }catch(IncompleteException ie) {
      //Expected exception, do nothing
    }
    
    try {
      new SimpleAnnotation.Builder().withType("TEST").withBounds(NoBounds.getInstance()).build();
      fail("Expected exception not thrown");
    }catch(IncompleteException ie) {
      //Expected exception, do nothing
    }
    
    Annotation a1 = new SimpleAnnotation.Builder().withType("TEST").withBounds(NoBounds.getInstance()).withContent("TEST_CONTENT").build();
    assertNotNull(a1.getId());
    assertEquals("TEST", a1.getType());
    assertEquals(NoBounds.getInstance(), a1.getBounds());
    assertEquals("TEST_CONTENT", a1.getContentName());
    assertEquals(EmptyImmutableProperties.getInstance(), a1.getProperties());
    
    Annotation a2 = new SimpleAnnotation.Builder().from(a1).withProperty("key1", Integer.valueOf(17)).withProperty("key2", false).build();
    assertNotNull(a2.getId());
    assertEquals("TEST", a2.getType());
    assertEquals(NoBounds.getInstance(), a2.getBounds());
    assertEquals("TEST_CONTENT", a2.getContentName());
    
    Map<String, Object> properties2 = a2.getProperties().getAll();
    assertEquals(2, properties2.size());
    assertEquals(Integer.valueOf(17), properties2.get("key1"));
    assertEquals(false, properties2.get("key2"));
    
    Annotation a3 = new SimpleAnnotation.Builder().withType("TEST").withBounds(NoBounds.getInstance()).withProperties(new InMemoryMutableProperties(properties2)).withContent("TEST_CONTENT").build();
    Map<String, Object> properties3 = a3.getProperties().getAll();
    assertEquals(2, properties3.size());
    assertEquals(Integer.valueOf(17), properties3.get("key1"));
    assertEquals(false, properties3.get("key2"));
  }
}
