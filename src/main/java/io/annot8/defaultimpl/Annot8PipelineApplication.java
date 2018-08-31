package io.annot8.defaultimpl;

import io.annot8.common.implementations.pipelines.PipelineBuilder;
import io.annot8.common.implementations.pipelines.SimplePipelineBuilder;
import io.annot8.common.implementations.registries.SimpleContentBuilderFactoryRegistry;
import java.util.function.Consumer;

public class Annot8PipelineApplication {

  private final Consumer<PipelineBuilder> pipelineBuilderConsumer;

  public Annot8PipelineApplication(Consumer<PipelineBuilder> pipelineBuilderConsumer) {
    this.pipelineBuilderConsumer = pipelineBuilderConsumer;
  }

  public void run() {
    PipelineBuilder builder = new SimplePipelineBuilder()
        .withContentBuilderFactory(new SimpleContentBuilderFactoryRegistry())
        ;


    if(pipelineBuilderConsumer != null) {
      pipelineBuilderConsumer.accept(builder);
    }



  }

  public static void main() {
    // Example of use

    new Annot8PipelineApplication(builder -> {

    }).run();



  }
}
