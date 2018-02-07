package io.annot8.defaultimpl.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.annot8.common.properties.EmptyImmutableProperties;
import io.annot8.common.stores.SaveFromBuilder;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.annotations.Group;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.annotations.SimpleGroup.Builder;
import io.annot8.test.TestConstants;
import io.annot8.test.TestItem;
import io.annot8.test.content.TestStringContent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleGroupTest {

  private TestItem item;
  private Annotation a1;
  private Annotation a2;
  private Annotation a3;
  private SaveFromBuilder<Group, Group> groupSaver;

  @BeforeEach
  public void beforeEach() throws IncompleteException {
    item = new TestItem();
    TestStringContent content = item.save(new TestStringContent());
    a1 = content.getAnnotations().create().save();
    a2 = content.getAnnotations().create().save();
    a3 = content.getAnnotations().create().save();

    groupSaver = mock(SaveFromBuilder.class);
    when(groupSaver.save(any(Group.class))).then(a -> a.getArguments()[0]);
  }


  @Test
  public void testIncompleteNothingSet() {
    assertThrows(IncompleteException.class, new Builder(item, groupSaver)::save);
  }

  @Test
  public void testIncompleteWithoutType() {
    assertThrows(IncompleteException.class, new Builder(item, groupSaver)
        .withAnnotation("source", a1)::save);
  }

  @Test
  public void testNewHasId() throws IncompleteException {
    Group group = new Builder(item, groupSaver)
        .withType(TestConstants.GROUP_TYPE)
        .newId()
        .save();

    assertNotNull(group.getId());

    verify(groupSaver, only()).save(group);
  }

  @Test
  public void testSimpleGroup() throws IncompleteException {

    Group g1 = new SimpleGroup.Builder(item, groupSaver)
        .withType(TestConstants.GROUP_TYPE)
        .withAnnotation("source", a1)
        .withAnnotation("target", a2)
        .save();
    assertNotNull(g1.getId());
    assertEquals(TestConstants.GROUP_TYPE, g1.getType());
    assertEquals(EmptyImmutableProperties.getInstance(), g1.getProperties());

    Map<String, Stream<Annotation>> annotations1 = g1.getAnnotations();
    assertEquals(2, annotations1.size());
    assertTrue(annotations1.containsKey("source"));
    List<Annotation> annotations1Source = annotations1.get("source").collect(Collectors.toList());
    assertEquals(1, annotations1Source.size());
    assertEquals(a1, annotations1Source.get(0));
    assertTrue(annotations1.containsKey("target"));
    List<Annotation> annotations1Target = annotations1.get("target").collect(Collectors.toList());
    assertEquals(1, annotations1Target.size());
    assertEquals(a2, annotations1Target.get(0));

    verify(groupSaver, only()).save(g1);


  }

  @Test
  public void testFromExisting() throws IncompleteException {
    Group g1 = new SimpleGroup.Builder(item, groupSaver)
        .withType(TestConstants.GROUP_TYPE)
        .withAnnotation("source", a1)
        .withAnnotation("target", a2)
        .save();

    clearInvocations(groupSaver);

    Group g2 = new SimpleGroup.Builder(item, groupSaver)
        .from(g1)
        .withAnnotation("target", a3)
        .save();
    Map<String, Stream<Annotation>> annotations2 = g2.getAnnotations();

    assertEquals(2, annotations2.size());
    assertTrue(annotations2.containsKey("source"));
    List<Annotation> annotations2Source = annotations2.get("source").collect(Collectors.toList());
    assertEquals(1, annotations2Source.size());
    assertEquals(a1, annotations2Source.get(0));
    assertTrue(annotations2.containsKey("target"));
    List<Annotation> annotations2Target = annotations2.get("target").collect(Collectors.toList());
    assertEquals(2, annotations2Target.size());
    assertTrue(annotations2Target.contains(a2));
    assertTrue(annotations2Target.contains(a3));

    verify(groupSaver, only()).save(g2);

  }

  @Test
  public void testFromExistingNoChange() throws IncompleteException {
    Group g1 = new SimpleGroup.Builder(item, groupSaver)
        .withType(TestConstants.GROUP_TYPE)
        .withAnnotation("source", a1)
        .withAnnotation("target", a2)
        .save();

    clearInvocations(groupSaver);

    Group g3 = new SimpleGroup.Builder(item, groupSaver)
        .from(g1)
        .save();
    assertEquals(g1, g3);

    verify(groupSaver, only()).save(g3);

  }

  @Test
  public void testFromExistingNewId() throws IncompleteException {
    Group g1 = new SimpleGroup.Builder(item, groupSaver)
        .withType(TestConstants.GROUP_TYPE)
        .withAnnotation("source", a1)
        .withAnnotation("target", a2)
        .save();

    clearInvocations(groupSaver);

    Group g3 = new SimpleGroup.Builder(item, groupSaver)
        .from(g1)
        .newId()
        .save();
    assertNotEquals(g1.getId(), g3.getId());

    verify(groupSaver, only()).save(g3);

  }
}
