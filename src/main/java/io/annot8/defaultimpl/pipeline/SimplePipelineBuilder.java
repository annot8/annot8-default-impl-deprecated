package io.annot8.defaultimpl.pipeline;

import io.annot8.common.content.FileContent;
import io.annot8.common.content.Text;
import io.annot8.common.factories.ContentBuilderFactory;
import io.annot8.common.factories.ItemFactory;
import io.annot8.core.components.Annot8Component;
import io.annot8.core.components.Processor;
import io.annot8.core.components.Resource;
import io.annot8.core.components.Source;
import io.annot8.core.data.Content;
import io.annot8.core.exceptions.Annot8Exception;
import io.annot8.core.settings.Settings;
import io.annot8.defaultimpl.content.SimpleFile;
import io.annot8.defaultimpl.content.SimpleText;
import io.annot8.defaultimpl.context.SimpleContext;
import io.annot8.defaultimpl.data.SimpleItem;
import io.annot8.defaultimpl.factories.SimpleContentBuilderFactoryRegistry;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimplePipelineBuilder {

  private final SimpleContentBuilderFactoryRegistry contentBuilderFactoryRegistry
      = new SimpleContentBuilderFactoryRegistry();


  // Use a linked hash map so the addition order = configuration order
  private final Map<Source, Settings> sourcesToConfiguration = new LinkedHashMap<>();
  private final Map<Processor, Settings> processorToConfiguration = new LinkedHashMap<>();
  private final Map<Resource, Settings> resourcesToConfiguration = new LinkedHashMap<>();
  private final Map<Resource, String> resourcesToId = new HashMap<>();


  public static void main(final String[] args) {

    final SimplePipelineBuilder builder = new SimplePipelineBuilder();
    builder.addContentBuilder(Text.class, new SimpleText.BuilderFactory());
    builder.addContentBuilder(FileContent.class, new SimpleFile.BuilderFactory());

    // If we had... some implementations
//    pipeline.addDataSource(new TxtDirectorySource(), new DirectorySourceSettings(args[0]));
//    pipeline.addProcessor(new Capitalise());
//    pipeline.addProcessor(new Email());
//    pipeline.addProcessor(new HashTag());
//    pipeline.addProcessor(new PrintMentions());

    SimplePipeline pipeline = builder.build();
    pipeline.run();
  }

  public <D, C extends Content<D>, I extends C> void addContentBuilder(Class<C> contentClass,
      ContentBuilderFactory<D, I> factory) {
    contentBuilderFactoryRegistry.register(contentClass, factory);
  }

  public void addResource(final String id, final Resource resource) {
    addResource(id, resource, null);
  }

  public void addDataSource(final Source source) {
    addDataSource(source, null);
  }

  public void addProcessor(final Processor processor) {
    addProcessor(processor, null);
  }

  public void addResource(final String id, final Resource resource, final Settings configuration) {
    resourcesToConfiguration.put(resource, configuration);
    resourcesToId.put(resource, id);
  }

  public void addDataSource(final Source source, final Settings configuration) {
    sourcesToConfiguration.put(source, configuration);
  }

  public void addProcessor(final Processor processor, final Settings configuration) {
    processorToConfiguration.put(processor, configuration);
  }

  public SimplePipeline build() {

    ItemFactory itemFactory = () -> new SimpleItem(contentBuilderFactoryRegistry);

    Map<String, Resource> configuredResources = new HashMap<>();

    resourcesToConfiguration.forEach((resource, settings) -> {

      if (configureComponent(itemFactory, configuredResources, resource, settings)) {
        String id = resourcesToId.get(resource);
        configuredResources.put(id, resource);
      }
    });

    List<Source> configuredSources = configureAllComponents(itemFactory, configuredResources,
        sourcesToConfiguration);
    List<Processor> configurePipelines = configureAllComponents(itemFactory, configuredResources,
        processorToConfiguration);

    return new SimplePipeline(configuredResources, configuredSources,
        configurePipelines);
  }

  private <T extends Annot8Component> List<T> configureAllComponents(ItemFactory itemFactory,
      Map<String, Resource> configuredResources, Map<T, Settings> componentToConfiguration) {

    return componentToConfiguration.entrySet().stream()
        .filter(e -> configureComponent(itemFactory, configuredResources, e.getKey(), e.getValue()))
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }

  private boolean configureComponent(ItemFactory itemFactory,
      Map<String, Resource> configuredResources, final Annot8Component component,
      final Settings configuration) {

    // TODO: COmpletely ignore capabilties here.. we could check for resources etc

    try {
      final SimpleContext context = new SimpleContext(itemFactory, configuration,
          configuredResources);
      component.configure(context);
      return true;
    } catch (final Annot8Exception e) {
      System.err.println("Failed to configure component " + component.getClass().getName());
    }
    return false;
  }


}