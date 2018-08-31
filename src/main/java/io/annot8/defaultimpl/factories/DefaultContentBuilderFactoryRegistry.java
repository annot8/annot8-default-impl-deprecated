package io.annot8.defaultimpl.factories;


import io.annot8.common.data.content.FileContent;
import io.annot8.common.data.content.InputStreamContent;
import io.annot8.common.data.content.Text;
import io.annot8.common.implementations.factories.ContentBuilderFactory;
import io.annot8.common.implementations.registries.ContentBuilderFactoryRegistry;
import io.annot8.core.data.Content;
import io.annot8.defaultimpl.content.SimpleFile;
import io.annot8.defaultimpl.content.SimpleInputStream;
import io.annot8.defaultimpl.content.SimpleText;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleContentBuilderFactoryRegistry implements ContentBuilderFactoryRegistry {

  private final Map<Class<? extends Content<?>>, ContentBuilderFactory<?, ?>> builders = new HashMap<>();


  public SimpleContentBuilderFactoryRegistry() {
    this(true);
  }

  public SimpleContentBuilderFactoryRegistry(boolean includeDefaultContentBuilders) {

    if(includeDefaultContentBuilders) {
      register(Text.class, new SimpleText.BuilderFactory());
      register(FileContent.class, new SimpleFile.BuilderFactory());
      register(InputStreamContent.class, new SimpleInputStream.BuilderFactory());

    }
  }

  public <D, C extends Content<D>, I extends C> void register(Class<C> contentClass,
      ContentBuilderFactory<D, I> contentBuilderFactory) {
    builders.put(contentClass, contentBuilderFactory);
  }

  @Override
  public <D, C extends Content<D>> Optional<ContentBuilderFactory<D, C>> get(
      Class<C> contentClass) {
    // This cast to C is correct, due to the way that the content is registered, we know that it tallies.
    // TODO: Slightly unsure about cast to D, I think it's correct in our case
    ContentBuilderFactory<D, C> contentBuilderFactory = (ContentBuilderFactory<D, C>) builders
        .get(contentClass);
    return Optional.ofNullable(contentBuilderFactory);
  }

}
