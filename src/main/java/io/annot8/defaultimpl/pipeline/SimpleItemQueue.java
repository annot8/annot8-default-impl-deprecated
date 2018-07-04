package io.annot8.defaultimpl.pipeline;

import io.annot8.core.components.Capabilities;
import io.annot8.core.components.Source;
import io.annot8.core.components.responses.SourceResponse;
import io.annot8.core.data.Item;
import io.annot8.defaultimpl.data.SimpleCapabilities;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SimpleItemQueue {

  private final Deque<Item> items = new ConcurrentLinkedDeque<>();

  public void add(Item item) {
    items.push(item);
  }

  public boolean hasItems() {
    return !items.isEmpty();
  }

  public Item next() {
    return items.pop();
  }

}