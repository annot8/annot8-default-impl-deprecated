package io.annot8.defaultimpl.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class EmptyImmutablePropertiesTest {
  @Test
  public void testEmptyImmutableProperties() {
    EmptyImmutableProperties eip = EmptyImmutableProperties.getInstance();
    assertNotNull(eip);
    assertEquals(eip, EmptyImmutableProperties.getInstance());
  }
}
