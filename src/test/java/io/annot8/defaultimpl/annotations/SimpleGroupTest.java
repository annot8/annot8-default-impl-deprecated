package io.annot8.defaultimpl.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.annotations.Group;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.properties.EmptyImmutableProperties;

public class SimpleGroupTest {
  @Test
  public void testSimpleGroup() throws IncompleteException {
    Annotation a1 = mock(Annotation.class);
    Annotation a2 = mock(Annotation.class);
    Annotation a3 = mock(Annotation.class);
    
    try {
      new SimpleGroup.Builder().build();
      fail("Expected exception not thrown");
    }catch(IncompleteException ie) {
      //Expected exception, do nothing
    }
    
    try {
      new SimpleGroup.Builder().withType("TEST").build();
      fail("Expected exception not thrown");
    }catch(IncompleteException ie) {
      //Expected exception, do nothing
    }
    
    try {
      new SimpleGroup.Builder().withType("TEST").withAnnotation("source", a1).build();
      fail("Expected exception not thrown");
    }catch(IncompleteException ie) {
      //Expected exception, do nothing
    }
    
    Group g1 = new SimpleGroup.Builder().withType("TEST").withAnnotation("source", a1).withAnnotation("target", a2).build();
    assertNotNull(g1.getId());
    assertEquals("TEST", g1.getType());
    assertEquals(EmptyImmutableProperties.getInstance(), g1.getProperties());
    
    Map<String, Stream<Annotation>> annotations1 = g1.getAnnotations();
    assertEquals(2, annotations1.size());
    assertTrue(annotations1.containsKey("source"));
    List<Annotation> annotations1Source = annotations1.get("source").collect(Collectors.toList());
    assertEquals(1, annotations1Source.size());
    assertEquals(a1, annotations1Source.get(0));
    assertTrue(annotations1.containsKey("target"));
    List<Annotation> annotations1Target = annotations1.get("target").collect(Collectors.toList());
    assertEquals(1, annotations1Target.size());
    assertEquals(a2, annotations1Target.get(0));
    
    Group g2 = new SimpleGroup.Builder().from(g1).withAnnotation("target", a3).build();
    Map<String, Stream<Annotation>> annotations2 = g2.getAnnotations();
    
    assertEquals(2, annotations2.size());
    assertTrue(annotations2.containsKey("source"));
    List<Annotation> annotations2Source = annotations2.get("source").collect(Collectors.toList());
    assertEquals(1, annotations2Source.size());
    assertEquals(a1, annotations2Source.get(0));
    assertTrue(annotations2.containsKey("target"));
    List<Annotation> annotations2Target = annotations2.get("target").collect(Collectors.toList());
    assertEquals(2, annotations2Target.size());
    assertTrue(annotations2Target.contains(a2));
    assertTrue(annotations2Target.contains(a3));
    
    Group g3 = new SimpleGroup.Builder().from(g1).build();
    assertEquals(g1, g3);
  }
}
