/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.defaultimpl;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.annot8.common.implementations.pipelines.queues.SimpleItemQueue;
import io.annot8.common.implementations.pipelines.runnable.RunnablePipeline;
import io.annot8.common.implementations.pipelines.runnable.RunnablePipelineBuilder;
import io.annot8.common.implementations.pipelines.runnable.SimpleRunnablePipelineBuilder;
import io.annot8.common.implementations.registries.ContentBuilderFactoryRegistry;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.factories.DefaultContentBuilderFactoryRegistry;

public class Annot8PipelineApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(Annot8PipelineApplication.class);

  private final Consumer<RunnablePipelineBuilder> pipelineBuilderConsumer;

  private final Consumer<ContentBuilderFactoryRegistry> contentBuilderFactoryRegistryConsumer;

  public Annot8PipelineApplication(Consumer<RunnablePipelineBuilder> pipelineBuilderConsumer) {
    this(pipelineBuilderConsumer, (c) -> {});
  }

  public Annot8PipelineApplication(
      Consumer<RunnablePipelineBuilder> pipelineBuilderConsumer,
      Consumer<ContentBuilderFactoryRegistry> contentBuilderFactoryRegistryConsumer) {
    this.pipelineBuilderConsumer = pipelineBuilderConsumer;
    this.contentBuilderFactoryRegistryConsumer = contentBuilderFactoryRegistryConsumer;
  }

  public void run() {
    try {
      RunnablePipeline pipeline = buildPipeline();
      runPipeline(pipeline);
    } catch (Exception e) {
      LOGGER.error("Unable to run pipeline", e);
    }
  }

  private void runPipeline(RunnablePipeline pipeline) throws Exception {
    try (pipeline) {
      pipeline.run();
    }
  }

  private RunnablePipeline buildPipeline() throws IncompleteException {
    RunnablePipelineBuilder builder = new SimpleRunnablePipelineBuilder();
    builder = configureBuilder(builder);

    if (pipelineBuilderConsumer != null) {
      pipelineBuilderConsumer.accept(builder);
    }

    return builder.build();
  }

  private RunnablePipelineBuilder configureBuilder(RunnablePipelineBuilder builder) {
    DefaultContentBuilderFactoryRegistry contentBuilderFactoryRegistry =
        new DefaultContentBuilderFactoryRegistry();
    contentBuilderFactoryRegistryConsumer.accept(contentBuilderFactoryRegistry);
    SimpleItemQueue itemQueue = new SimpleItemQueue();
    return builder.withQueue(itemQueue);
  }

  public static void main() {
    // Example of use

    new Annot8PipelineApplication(
            builder -> {

              // builder.addSource(FileSource.class);
              // builder.addProcessor(EmailProcessor.class);

            })
        .run();
  }
}
