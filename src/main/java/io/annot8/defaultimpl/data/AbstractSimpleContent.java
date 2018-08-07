package io.annot8.defaultimpl.data;

import java.util.UUID;
import io.annot8.common.factories.ContentBuilderFactory;
import io.annot8.common.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.properties.Properties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.properties.SimpleImmutableProperties;
import io.annot8.defaultimpl.stores.SimpleAnnotationStore;

public abstract class AbstractSimpleContent<D> implements Content<D> {

  private final String id;
  private final String name;
  private final AnnotationStore annotations;
  private final ImmutableProperties properties;
  private final D data;

  protected AbstractSimpleContent(String id, AnnotationStore annotations, String name,
      ImmutableProperties properties, D data) {
    this.id = id;
    this.name = name;
    this.annotations = annotations;
    this.properties = properties;
    this.data = data;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public D getData() {
    return data;
  }

  @Override
  public Class getDataClass() {
    return data.getClass();
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

  public abstract static class Builder<D, C extends Content<D>> implements Content.Builder<C, D> {

    private final SaveCallback<C, C> saver;
    private String name;
    private String id;
    private final ImmutableProperties.Builder properties = new SimpleImmutableProperties.Builder();
    private D data;

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
    public Content.Builder<C, D> withData(D data) {
      this.data = data;
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

      if (data == null) {
        throw new IncompleteException("Data is required");
      }

      SimpleAnnotationStore annotations = new SimpleAnnotationStore(name);
      C content = create(id, annotations, name, properties.save(), data);
      return saver.save(content);
    }

    protected abstract C create(String id, AnnotationStore annotations, String name,
        ImmutableProperties properties, D data);

  }

  public abstract static class BuilderFactory<D, C extends Content<D>>
      implements ContentBuilderFactory<D, C> {

    private final Class<D> dataClass;
    private final Class<C> contentClass;

    protected BuilderFactory(Class<D> dataClass, Class<C> contentClass) {
      this.dataClass = dataClass;
      this.contentClass = contentClass;
    }

    @Override
    public Class<D> getDataClass() {
      return dataClass;
    }

    @Override
    public Class<C> getContentClass() {
      return contentClass;
    }
  }
}
