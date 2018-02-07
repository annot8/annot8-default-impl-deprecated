package io.annot8.defaultimpl.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.annot8.core.data.Tags;
import io.annot8.core.exceptions.IncompleteException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class SimpleTagsTest {

  @Test
  public void testNewTags() throws IncompleteException {
    Tags tags1 = new SimpleTags.Builder()
        .addTag("TAG1")
        .addTags(Arrays.asList("TAG2", "TAG3"))
        .save();

    List<String> tagsList1 = tags1.get().collect(Collectors.toList());
    assertEquals(3, tagsList1.size());
    assertTrue(tagsList1.contains("TAG1"));
    assertTrue(tagsList1.contains("TAG2"));
    assertTrue(tagsList1.contains("TAG3"));

  }

  @Test
  public void testFromExisting() throws IncompleteException {

    Tags tags1 = new SimpleTags.Builder()
        .addTag("TAG1")
        .save();

    Tags tags2 = new SimpleTags.Builder()
        .from(tags1)
        .addTag("TAG4")
        .save();

    List<String> tagsList2 = tags2.get().collect(Collectors.toList());
    assertEquals(2, tagsList2.size());
    assertTrue(tagsList2.contains("TAG1"));
    assertTrue(tagsList2.contains("TAG4"));
  }
}
