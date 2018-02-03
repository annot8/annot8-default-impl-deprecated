package io.annot8.defaultimpl.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import io.annot8.core.data.Tags;
import io.annot8.core.exceptions.IncompleteException;

public class InMemoryTagsTest {

  @Test
  public void testTags() throws IncompleteException {
    Tags tags1 = new InMemoryTags.Builder()
        .addTag("TAG1")
        .addTags(Arrays.asList("TAG2", "TAG3"))
        .build();

    List<String> tagsList1 = tags1.get().collect(Collectors.toList());
    assertEquals(3, tagsList1.size());
    assertTrue(tagsList1.contains("TAG1"));
    assertTrue(tagsList1.contains("TAG2"));
    assertTrue(tagsList1.contains("TAG3"));

    Tags tags2 = new InMemoryTags.Builder()
        .from(tags1)
        .addTag("TAG4")
        .build();

    List<String> tagsList2 = tags2.get().collect(Collectors.toList());
    assertEquals(4, tagsList2.size());
    assertTrue(tagsList2.contains("TAG1"));
    assertTrue(tagsList2.contains("TAG2"));
    assertTrue(tagsList2.contains("TAG3"));
    assertTrue(tagsList2.contains("TAG4"));
  }
}
