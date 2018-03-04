package io.annot8.defaultimpl.references;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.core.annotations.Group;
import io.annot8.core.data.Item;
import io.annot8.core.stores.GroupStore;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SimpleGroupReferenceTest {

  @Test
  void to() {
    Item item = mock(Item.class);
    Group group = mock(Group.class);
    when(group.getId()).thenReturn("id");

    SimpleGroupReference reference = SimpleGroupReference.to(item, group);

    assertEquals("id", reference.getGroupId());

  }

  @Test
  void newGroupReference() {

    Item item = mock(Item.class);
    GroupStore groupStore = mock(GroupStore.class);
    Group group = mock(Group.class);

    SimpleGroupReference reference = new SimpleGroupReference(item, "1");

    assertEquals("1", reference.getGroupId());

    when(item.getGroups()).thenReturn(groupStore);
    when(groupStore.getById(Mockito.eq("1"))).thenReturn(Optional.of(group));

    assertEquals(group, reference.toGroup().get());
  }

}