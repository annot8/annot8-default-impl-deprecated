package io.annot8.defaultimpl.references;

import io.annot8.common.implementations.references.AbstractGroupReference;
import io.annot8.core.annotations.Group;
import io.annot8.core.data.Item;
import java.util.Optional;

/**
 * A reference which will always retrieve the latest group from the appropriate group store.
 *
 * Does not hold a reference to the group.
 */
public class SimpleGroupReference extends AbstractGroupReference {

  private final Item item;

  private final String groupId;

  /**
   * New reference either from another reference or manually created.
   */
  public SimpleGroupReference(Item item, String groupId) {
    this.item = item;
    this.groupId = groupId;
  }

  /**
   * Create a reference from a group instance.
   */
  public static SimpleGroupReference to(Item item, Group group) {
    return new SimpleGroupReference(item, group.getId());
  }

  @Override
  public String getGroupId() {
    return groupId;
  }

  @Override
  public Optional<Group> toGroup() {
    return item.getGroups()
        .getById(groupId);
  }
}
