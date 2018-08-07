package io.annot8.defaultimpl.pipeline;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import io.annot8.core.data.Item;

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