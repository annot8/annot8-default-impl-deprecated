package io.annot8.defaultimpl.data;

import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.stores.AnnotationStore;

public abstract class AbstractSimpleContent<D> extends AbstractContent<D> {


  private final D data;

  protected AbstractSimpleContent(String id, AnnotationStore annotations, String name,
      ImmutableProperties properties, D data) {
    super(id, annotations, name, properties);

    this.data = data;
  }

  @Override
  public D getData() {
    return data;
  }

  @Override
  public Class getDataClass() {
    return data.getClass();
  }


  public abstract static class Builder<D, C extends Content<D>> extends AbstractContent.Builder<D, C> {

    private D data;

    public Builder(SaveCallback<C, C> saver) {
      super(saver);
    }

    @Override
    public Content.Builder<C, D> withData(D data) {
      this.data = data;
      return this;
    }

    protected C create(String id, AnnotationStore annotations, String name,
        ImmutableProperties properties) throws IncompleteException {

      if (data == null) {
        throw new IncompleteException("Data is required");
      }

      return create(id, annotations, name, properties, data);

    }

    protected abstract C create(String id, AnnotationStore annotations, String name,
        ImmutableProperties properties, D data) throws IncompleteException;
  }

}
