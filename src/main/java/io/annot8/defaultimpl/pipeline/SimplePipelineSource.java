package io.annot8.defaultimpl.pipeline;

import io.annot8.core.components.Capabilities;
import io.annot8.core.components.Source;
import io.annot8.core.components.responses.SourceResponse;
import io.annot8.core.data.Item;
import io.annot8.core.settings.Settings;
import io.annot8.defaultimpl.data.SimpleCapabilities;
import java.util.ArrayDeque;
import java.util.Deque;

public class SimplePipelineSource implements Source {

  private final Deque<Item> items = new ArrayDeque<>();

  public synchronized void add(final Item item) {
    items.addLast(item);
  }

  @Override
  public synchronized SourceResponse read() {
    if (!hasItems()) {
      return SourceResponse.empty();
    } else {
      return SourceResponse.ok(next());
    }
  }


  private boolean hasItems() {
    return items.isEmpty();
  }

  private Item next() {
    return items.pop();
  }


  // TODO; Capabilities doesn't work here (we have not idea whas it in item), but that's a special case.
  @Override
  public Capabilities getCapabilities(Settings settings) {
    return new SimpleCapabilities.Builder().save();
  }
}