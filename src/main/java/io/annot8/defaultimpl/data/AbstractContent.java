package io.annot8.defaultimpl.data;

import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.properties.Properties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.common.implementations.properties.MapImmutableProperties;
import io.annot8.defaultimpl.stores.SimpleAnnotationStore;
import java.util.UUID;

public abstract class AbstractContent<D> implements Content<D> {

  private final String id;
  private final String name;
  private final AnnotationStore annotations;
  private final ImmutableProperties properties;

  protected AbstractContent(String id, AnnotationStore annotations, String name,
      ImmutableProperties properties) {
    this.id = id;
    this.name = name;
    this.annotations = annotations;
    this.properties = properties;
  }

  @Override
  public String getId() {
    return id;
  }


  @Override
  public AnnotationStore getAnnotations() {
    return annotations;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }

  public abstract static class Builder<D, C extends Content<?>> implements Content.Builder<C, D> {

    private final SaveCallback<C, C> saver;
    private final ImmutableProperties.Builder properties = new MapImmutableProperties.Builder();
    private String name;
    private String id;

    public Builder(SaveCallback<C, C> saver) {
      this.saver = saver;
    }

    @Override
    public Content.Builder<C, D> withId(String id) {
      this.id = id;
      return this;
    }

    @Override
    public Content.Builder<C, D> withName(String name) {
      this.name = name;
      return this;
    }

    @Override
    public Content.Builder<C, D> from(C from) {
      return this;
    }

    @Override
    public Content.Builder<C, D> withProperty(String key, Object value) {
      properties.withProperty(key, value);
      return this;
    }

    @Override
    public Content.Builder<C, D> withProperties(Properties properties) {
      this.properties.withProperties(properties);
      return this;
    }

    @Override
    public Content.Builder<C, D> withoutProperty(String key, Object value) {
      properties.withoutProperty(key, value);
      return this;
    }

    @Override
    public Content.Builder<C, D> withoutProperty(String key) {
      properties.withoutProperty(key);
      return this;
    }

    @Override
    public C save() throws IncompleteException {
      if (id == null) {
        id = UUID.randomUUID().toString();
      }

      if (name == null) {
        throw new IncompleteException("Name is required");
      }

      SimpleAnnotationStore annotations = new SimpleAnnotationStore(name);
      C content = create(id, annotations, name, properties.save());
      return saver.save(content);
    }

    protected abstract C create(String id, AnnotationStore annotations, String name,
        ImmutableProperties properties) throws IncompleteException;

  }


}
