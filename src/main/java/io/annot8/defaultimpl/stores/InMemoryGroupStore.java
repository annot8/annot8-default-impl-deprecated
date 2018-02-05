package io.annot8.defaultimpl.stores;

import io.annot8.core.annotations.Group;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.stores.GroupStore;
import io.annot8.defaultimpl.annotations.SimpleGroup;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * In memory implementation, backed by a HashMap, of GroupStore
 */
public class InMemoryGroupStore implements GroupStore {

  private Map<String, Group> groups = new HashMap<>();
  private final Class<? extends Group.Builder> groupBuilderClass;

  /**
   * Construct a new instance of this class using SimpleGroup.Builder as the annotation builder
   */
  public InMemoryGroupStore() {
    groupBuilderClass = SimpleGroup.Builder.class;
  }

  /**
   * Construct a new instance of this class using the specified Group.Builder as the annotation
   * builder
   */
  public InMemoryGroupStore(Class<? extends Group.Builder> groupBuilderClass) {
    this.groupBuilderClass = groupBuilderClass;
  }

  @Override
  public Group.Builder getBuilder() {
    try {
      return groupBuilderClass.getConstructor().newInstance();
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      //TODO: Log error
      return new SimpleGroup.Builder();
    }
  }

  @Override
  public Group save(Group.Builder builder) throws IncompleteException {
    Group g = builder.build();

    groups.put(g.getId(), g);

    return g;
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
}
