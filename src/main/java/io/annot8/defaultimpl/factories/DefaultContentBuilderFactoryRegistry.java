package io.annot8.defaultimpl.factories;


import io.annot8.common.data.content.FileContent;
import io.annot8.common.data.content.InputStreamContent;
import io.annot8.common.data.content.Text;
import io.annot8.common.implementations.registries.SimpleContentBuilderFactoryRegistry;
import io.annot8.defaultimpl.content.SimpleFile;
import io.annot8.defaultimpl.content.SimpleInputStream;
import io.annot8.defaultimpl.content.SimpleText;

public class DefaultContentBuilderFactoryRegistry extends SimpleContentBuilderFactoryRegistry {

  public DefaultContentBuilderFactoryRegistry() {
    this(true);
  }

  public DefaultContentBuilderFactoryRegistry(boolean includeDefaultContentBuilders) {

    if(includeDefaultContentBuilders) {
      register(Text.class, new SimpleText.BuilderFactory());
      register(FileContent.class, new SimpleFile.BuilderFactory());
      register(InputStreamContent.class, new SimpleInputStream.BuilderFactory());

    }
  }

}
