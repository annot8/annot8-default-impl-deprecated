package io.annot8.defaultimpl.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import io.annot8.core.data.Tags;
import io.annot8.core.exceptions.IncompleteException;

/**
 * Provides an in-memory implementation of the Tags interface,
 * backed by a HashSet.
 */
public class InMemoryTags implements Tags {

  private final Set<String> tags;
  
  /**
   * Private constructor, used by the Builder
   */
  private InMemoryTags(Set<String> tags) {
    this.tags = tags;
  }
  
  @Override
  public Stream<String> get() {
    return tags.stream();
  }
  
  /**
   * Builder class for InMemoryTags
   */
  public static class Builder implements Tags.Builder{
    private Set<String> tags = new HashSet<>();
    
    @Override
    public io.annot8.core.data.Tags.Builder from(Tags from) {
      from.get().forEach(this::addTag);
      return this;
    }

    @Override
    public Tags build() throws IncompleteException {
      return new InMemoryTags(tags);
    }

    @Override
    public io.annot8.core.data.Tags.Builder addTag(String tag) {
      this.tags.add(tag);
      return this;
    }

    @Override
    public io.annot8.core.data.Tags.Builder addTags(Collection<String> tags) {
      this.tags.addAll(tags);
      return this;
    }
    
  }

}
