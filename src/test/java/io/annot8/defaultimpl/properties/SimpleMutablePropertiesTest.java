package io.annot8.defaultimpl.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import io.annot8.core.properties.MutableProperties;

public class SimpleMutablePropertiesTest {
  @Test
  public void testMutableProperties() {
    MutableProperties props = new SimpleMutableProperties();
    
    props.set("key1", "Hello World");
    props.set("key2", Integer.valueOf(17));
    props.set("key3", Long.valueOf(17));
    props.set("key1", "Overwritten");
    
    assertTrue(props.has("key3"));
    Optional<Object> removed = props.remove("key3");
    assertTrue(removed.isPresent());
    assertEquals(Long.valueOf(17), removed.get());
    assertFalse(props.has("key3"));
    
    assertFalse(props.remove("key4").isPresent());
    
    Map<String, Object> map = props.getAll();
    testMap(map);
    
    MutableProperties props2 = new SimpleMutableProperties(props);
    testMap(props2.getAll());
    
    MutableProperties props3 = new SimpleMutableProperties(map);
    testMap(props3.getAll());
  }
  
  private void testMap(Map<String, Object> map) {
    assertEquals(2, map.size());
    assertEquals("Overwritten", map.get("key1"));
    assertEquals(Integer.valueOf(17), map.get("key2"));
  }
}