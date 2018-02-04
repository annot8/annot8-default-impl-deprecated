package io.annot8.defaultimpl.bounds;

import io.annot8.core.bounds.Bounds;

/**
 * Implementation of Bounds indicating that an annotation does
 * not have any bounds (i.e. it is metadata).
 * 
 * This class is a singleton, and should be accessed via getInstance()
 */
public final class NoBounds implements Bounds {
  private static final NoBounds INSTANCE = new NoBounds();
  
  private NoBounds() {
    //Empty constructor
  }
  
  /**
   * Return the singleton instance of NoBounds
   */
  public static NoBounds getInstance() {
    return INSTANCE;
  }
  
  @Override
  public String toString() {
    return "NoBounds";
  }
}
