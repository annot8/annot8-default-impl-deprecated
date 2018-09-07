package io.annot8.defaultimpl;

import io.annot8.common.implementations.pipelines.Pipeline;
import io.annot8.common.implementations.pipelines.PipelineBuilder;
import io.annot8.common.implementations.pipelines.SimpleItemQueue;
import io.annot8.common.implementations.pipelines.SimplePipelineBuilder;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.factories.DefaultContentBuilderFactoryRegistry;
import io.annot8.defaultimpl.factories.DefaultItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class Annot8PipelineApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(Annot8PipelineApplication.class);

  private final Consumer<PipelineBuilder> pipelineBuilderConsumer;

  public Annot8PipelineApplication(Consumer<PipelineBuilder> pipelineBuilderConsumer) {
    this.pipelineBuilderConsumer = pipelineBuilderConsumer;
  }

  public void run() {
    try {
      Pipeline pipeline = buildPipeline();

      runPipeline(pipeline);
    } catch(Exception e) {
      LOGGER.error("Unable to run pipeline", e);
    }
  }

  private void runPipeline(Pipeline pipeline) throws Exception {
    try(pipeline) {
      pipeline.run();
    }
  }

  private Pipeline buildPipeline() throws IncompleteException {

    PipelineBuilder builder = new SimplePipelineBuilder();

    builder = configureBuilder(builder);

    if (pipelineBuilderConsumer != null) {
      pipelineBuilderConsumer.accept(builder);
    }

    return builder.build();
  }

  private PipelineBuilder configureBuilder(PipelineBuilder builder) {
    DefaultContentBuilderFactoryRegistry contentBuilderFactoryRegistry = new DefaultContentBuilderFactoryRegistry();
    SimpleItemQueue itemQueue = new SimpleItemQueue();
    return builder
        .withItemFactory(new DefaultItemFactory(contentBuilderFactoryRegistry, itemQueue::add))
        .withContentBuilderFactory(contentBuilderFactoryRegistry)
        .withItemQueue(itemQueue);

  }

  public static void main() {
    // Example of use

    new Annot8PipelineApplication(builder -> {

      // builder.addSource(FileSource.class);
      // builder.addProcessor(EmailProcessor.class);

    }).run();



  }
}
