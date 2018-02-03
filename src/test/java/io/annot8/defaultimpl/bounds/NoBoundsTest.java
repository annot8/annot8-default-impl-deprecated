package io.annot8.defaultimpl.bounds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class NoBoundsTest {
  @Test
  public void testNoBounds() {
    NoBounds nb = NoBounds.getInstance();
    assertNotNull(nb);
    assertEquals(nb, NoBounds.getInstance());
  }
}
