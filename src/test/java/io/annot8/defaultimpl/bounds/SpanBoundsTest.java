package io.annot8.defaultimpl.bounds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SpanBoundsTest {
  @Test
  public void testSpanBounds() {
    SpanBounds sb = new SpanBounds(5, 10);

    assertEquals(5, sb.getBegin());
    assertEquals(10, sb.getEnd());
  }
}
