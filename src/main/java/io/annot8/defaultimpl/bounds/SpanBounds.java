package io.annot8.defaultimpl.bounds;

import java.util.Objects;
import io.annot8.core.bounds.Bounds;

/**
 * Implementation of Bounds for a simple 2D span,
 * such as an offset of text.
 */
public class SpanBounds implements Bounds {
  private final int begin;
  private final int end;
  
  /**
   * Create a new object with the specified begin and end values
   */
  public SpanBounds(final int begin, final int end) {
    this.begin = begin;
    this.end = end;
  }

  /**
   * Return the begin position of this object
   */
  public int getBegin() {
    return begin;
  }

  /**
   * Return the end position of this object
   */
  public int getEnd() {
    return end;
  }
  
  @Override
  public String toString() {
    return this.getClass().getName() + " [begin=" + begin + ", end=" + end + "]";
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(begin, end);
  }
  
  @Override
  public boolean equals(Object o) {
    if(!(o instanceof SpanBounds))
      return false;
    
    SpanBounds sb = (SpanBounds) o;
    
    return Objects.equals(begin, sb.getBegin()) && Objects.equals(end, sb.getEnd());
  }
}
