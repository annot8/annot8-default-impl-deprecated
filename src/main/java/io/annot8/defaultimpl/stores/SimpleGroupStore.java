package io.annot8.defaultimpl.stores;

import io.annot8.common.implementations.factories.GroupBuilderFactory;
import io.annot8.core.annotations.Group;
import io.annot8.core.data.Item;
import io.annot8.core.stores.GroupStore;
import io.annot8.defaultimpl.annotations.SimpleGroup;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In memory implementation, backed by a HashMap, of GroupStore
 */
public class SimpleGroupStore implements GroupStore {

  private final Item item;
  private final Map<String, Group> groups = new ConcurrentHashMap<>();
  private final GroupBuilderFactory<Group> groupBuilderFactory;

  /**
   * Construct a new instance of this class using SimpleGroup.AbstractContentBuilder as the annotation builder for
   * the provided item.
   */
  public SimpleGroupStore(Item item) {
    this.item = item;
    this.groupBuilderFactory = (forItem, groupStore, saver) -> new SimpleGroup.Builder(forItem,
        saver);
  }

  /**
   * Construct a new instance of this class using a custom group builder.
   */
  public SimpleGroupStore(Item item, GroupBuilderFactory<Group> groupBuilderFactory) {
    this.item = item;
    this.groupBuilderFactory = groupBuilderFactory;
  }

  @Override
  public Group.Builder getBuilder() {
    return groupBuilderFactory.create(item, this, this::save);
  }

  private Group save(Group group) {
    groups.put(group.getId(), group);
    return group;
  }

  @Override
  public void delete(Group group) {
    groups.remove(group.getId(), group);
  }

  @Override
  public Stream<Group> getAll() {
    return groups.values().stream();
  }

  @Override
  public Optional<Group> getById(String s) {
    return Optional.ofNullable(groups.get(s));
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [groups=" + groups.size() + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(groups);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GroupStore)) {
      return false;
    }

    GroupStore gs = (GroupStore) obj;

    Set<Group> allGroups = gs.getAll().collect(Collectors.toSet());

    return Objects.equals(new HashSet<>(groups.values()), allGroups);
  }
}
