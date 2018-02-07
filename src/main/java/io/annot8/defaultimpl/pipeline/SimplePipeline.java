package io.annot8.defaultimpl.pipeline;

import io.annot8.common.factories.ItemFactory;
import io.annot8.core.components.Processor;
import io.annot8.core.components.Resource;
import io.annot8.core.components.Source;
import io.annot8.core.components.responses.ProcessorResponse;
import io.annot8.core.components.responses.ProcessorResponse.Status;
import io.annot8.core.components.responses.SourceResponse;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.Annot8Exception;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SimplePipeline {

  private final SimplePipelineSource pipelineSource = new SimplePipelineSource();
  private final ItemFactory itemFactory;
  private final Map<String, Resource> resources;
  private final List<Source> sources;
  private final List<Processor> processors;

  public SimplePipeline(
      ItemFactory itemFactory, Map<String, Resource> resources,
      List<Source> sources, List<Processor> processors) {
    this.itemFactory = itemFactory;
    this.resources = resources;
    this.sources = sources;
    this.processors = processors;
  }

  public void run() {
    for (final Source source : sources) {
      process(source);
    }
  }

  private void process(final Source source) {
    SourceResponse.Status status;
    do {
      final SourceResponse response = source.read();
      status = response.getStatus();
      if (status == SourceResponse.Status.OK) {
        response.getItems().forEach(this::process);
      }
    } while (status == SourceResponse.Status.OK);

  }

  private void process(final Item item) {

    processItem(item);

    // After you've finished with one item cleaer our the items its added
    SourceResponse response;
    while ((response = pipelineSource.read()).getStatus() == SourceResponse.Status.OK) {
      response.getItems().forEach(this::processItem);
    }
  }

  private void processItem(final Item item) {
    for (final Processor processor : processors) {
      try {
        final ProcessorResponse response = processor.process(item);

        final ProcessorResponse.Status status = response.getStatus();
        if (status == ProcessorResponse.Status.OK || status == Status.ITEM_STOP) {
          final Stream<Item> items = response.getItems();

          items.filter(i -> i != item).forEach(pipelineSource::add);

          // Are we done with this item?
          if (status == Status.ITEM_STOP) {
            return;
          }

        } else if (status == ProcessorResponse.Status.PIPELINE_ERROR) {
          System.err.println("Pipeline problem, exiting");

          System.exit(1);
        } else if (status == ProcessorResponse.Status.ITEM_ERROR) {
          System.err.println("Item problem, skipping rest of pipeline");
          return;
        }

      } catch (final Annot8Exception e) {
        System.err.println(
            "Failed to process data item with processor " + processor.getClass().getName());
      }
    }
  }

}