package io.annot8.defaultimpl.data;

import io.annot8.core.data.Tags;
import io.annot8.core.exceptions.IncompleteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides an in-memory implementation of the Tags interface, backed by a HashSet.
 */
public class SimpleTags implements Tags {

  private final Set<String> tags;

  /**
   * Private constructor, used by the Builder
   */
  private SimpleTags(Set<String> tags) {
    this.tags = tags;
  }

  @Override
  public Stream<String> get() {
    return tags.stream();
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [" + tags.stream().collect(Collectors.joining(",")) + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(tags);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Tags)) {
      return false;
    }

    Tags t = (Tags) o;
    Set<String> set = t.get().collect(Collectors.toSet());

    return Objects.equals(this.tags, set);
  }

  /**
   * Builder class for InMemoryTags
   */
  public static class Builder implements Tags.Builder {

    private final Set<String> tags = new HashSet<>();

    @Override
    public io.annot8.core.data.Tags.Builder from(Tags from) {
      from.get().forEach(this::addTag);
      return this;
    }

    @Override
    public Tags save() throws IncompleteException {
      return new SimpleTags(tags);
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
