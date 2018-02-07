package io.annot8.defaultimpl.data;

import io.annot8.common.factories.ContentBuilderFactory;
import io.annot8.common.stores.SaveFromBuilder;
import io.annot8.core.data.Content;
import io.annot8.core.data.Tags;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.properties.Properties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.defaultimpl.properties.SimpleImmutableProperties;
import io.annot8.defaultimpl.stores.SimpleAnnotationStore;

public abstract class AbstractSimpleContent<D> implements Content<D> {

  private final String name;
  private final Tags tags;
  private final AnnotationStore annotations;
  private final ImmutableProperties properties;
  private final D data;

  protected AbstractSimpleContent(AnnotationStore annotations, String name, Tags tags,
      ImmutableProperties properties, D data) {
    this.name = name;
    this.tags = tags;
    this.annotations = annotations;
    this.properties = properties;
    this.data = data;
  }

  @Override
  public D getData() {
    return data;
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

  @Override
  public Tags getTags() {
    return tags;
  }

  public abstract static class Builder<D, C extends Content<D>> implements Content.Builder<C, D> {

    private final SaveFromBuilder<C, C> saver;
    private String name;
    private final Tags.Builder tags = new SimpleTags.Builder();
    private final ImmutableProperties.Builder properties = new SimpleImmutableProperties.Builder();
    private D data;

    public Builder(SaveFromBuilder<C, C> saver) {
      this.saver = saver;
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
    public C save() throws IncompleteException {

      if (name == null) {
        throw new IncompleteException("Name is required");
      }

      if (data == null) {
        throw new IncompleteException("Data is required");
      }

      SimpleAnnotationStore annotations = new SimpleAnnotationStore(name);
      C content = create(annotations, name, tags.save(), properties.save(), data);
      return saver.save(content);
    }

    protected abstract C create(AnnotationStore annotations, String name, Tags tags,
        ImmutableProperties properties,
        D data);

    @Override
    public Content.Builder<C, D> withTag(String tag) {
      this.tags.addTag(tag);
      return this;
    }

    @Override
    public Content.Builder<C, D> withTags(Tags tags) {
      tags.get().forEach(this.tags::addTag);
      return this;
    }
  }

  public abstract static class BuilderFactory<D, C extends Content<D>> implements
      ContentBuilderFactory<D, C> {

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
