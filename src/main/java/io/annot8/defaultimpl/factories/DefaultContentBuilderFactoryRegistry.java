/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.defaultimpl.factories;

import io.annot8.common.data.content.FileContent;
import io.annot8.common.data.content.InputStreamContent;
import io.annot8.common.data.content.Text;
import io.annot8.common.implementations.registries.SimpleContentBuilderFactoryRegistry;
import io.annot8.defaultimpl.content.DefaultFile;
import io.annot8.defaultimpl.content.DefaultInputStream;
import io.annot8.defaultimpl.content.DefaultText;

public class DefaultContentBuilderFactoryRegistry extends SimpleContentBuilderFactoryRegistry {

  public DefaultContentBuilderFactoryRegistry() {
    this(true);
  }

  public DefaultContentBuilderFactoryRegistry(boolean includeDefaultContentBuilders) {

    if (includeDefaultContentBuilders) {
      register(Text.class, new DefaultText.BuilderFactory());
      register(FileContent.class, new DefaultFile.BuilderFactory());
      register(InputStreamContent.class, new DefaultInputStream.BuilderFactory());
    }
  }
}
