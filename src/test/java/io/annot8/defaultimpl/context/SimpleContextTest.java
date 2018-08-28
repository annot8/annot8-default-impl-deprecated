package io.annot8.defaultimpl.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.core.components.Resource;
import io.annot8.core.data.ItemFactory;
import io.annot8.core.settings.Settings;
import io.annot8.testing.testimpl.TestItem;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleContextTest {

  private ItemFactory itemFactory;

  @BeforeEach
  public void beforeEach() {
    itemFactory = mock(ItemFactory.class);
    when(itemFactory.create()).thenReturn(new TestItem());
  }


  @Test
  public void testSimpleContextDefault() {
    Resource r1 = mock(Resource.class);
    Resource r2 = new TestResource();

    SimpleContext context = new SimpleContext(itemFactory);
    context.addResource("resource1", r1);
    context.addResource("resource2", r2);

    assertTrue(context.getSettings().count() == 0);

    assertFalse(context.getResource("foo", Resource.class).isPresent());
    assertFalse(context.getResource("resource1", TestResource.class).isPresent());
    assertTrue(context.getResource("resource1", Resource.class).isPresent());

    List<String> keys = context.getResourceKeys().collect(Collectors.toList());
    assertEquals(2, keys.size());
    assertTrue(keys.contains("resource1"));
    assertTrue(keys.contains("resource2"));

    List<TestResource> resources = context.getResources(TestResource.class)
        .collect(Collectors.toList());
    assertEquals(1, resources.size());
    assertEquals(r2, resources.get(0));
  }

  @Test
  public void testSimpleContextMap() {
    Resource r1 = mock(Resource.class);
    Resource r2 = new TestResource();

    Map<String, Resource> r = new HashMap<>();
    r.put("resource1", r1);
    r.put("resource2", r2);

    SimpleContext context = new SimpleContext(itemFactory, r);

    assertTrue(context.getSettings().count() == 0);

    assertFalse(context.getResource("foo", Resource.class).isPresent());
    assertFalse(context.getResource("resource1", NotTestResource.class).isPresent());
    assertTrue(context.getResource("resource2", Resource.class).isPresent());
    assertTrue(context.getResource("resource2", TestResource.class).isPresent());

    List<String> keys = context.getResourceKeys().collect(Collectors.toList());
    assertEquals(2, keys.size());
    assertTrue(keys.contains("resource1"));
    assertTrue(keys.contains("resource2"));

    List<TestResource> resources = context.getResources(TestResource.class)
        .collect(Collectors.toList());
    assertEquals(1, resources.size());
    assertEquals(r2, resources.get(0));
  }

  @Test
  public void testSimpleContextSettings() {
    Settings s = mock(Settings.class);

    SimpleContext context = new SimpleContext(itemFactory, Arrays.asList(s));

    assertEquals(1, context.getSettings().count());
    assertEquals(s, context.getSettings().findFirst().get());

    List<String> keys = context.getResourceKeys().collect(Collectors.toList());
    assertTrue(keys.isEmpty());

    List<Resource> resources = context.getResources(Resource.class).collect(Collectors.toList());
    assertTrue(resources.isEmpty());
  }

  @Test
  public void testSimpleContextSettingsAndMap() {
    Resource r1 = mock(Resource.class);
    Resource r2 = new TestResource();
    Settings s = mock(Settings.class);

    Map<String, Resource> r = new HashMap<>();
    r.put("resource1", r1);
    r.put("resource2", r2);

    SimpleContext context = new SimpleContext(itemFactory, Arrays.asList(s), r);

    assertEquals(1, context.getSettings().count());
    assertEquals(s, context.getSettings().findFirst().get());

    assertFalse(context.getResource("foo", Resource.class).isPresent());
    assertFalse(context.getResource("resource1", TestResource.class).isPresent());
    assertTrue(context.getResource("resource1", Resource.class).isPresent());

    List<String> keys = context.getResourceKeys().collect(Collectors.toList());
    assertEquals(2, keys.size());
    assertTrue(keys.contains("resource1"));
    assertTrue(keys.contains("resource2"));

    List<TestResource> resources = context.getResources(TestResource.class)
        .collect(Collectors.toList());
    assertEquals(1, resources.size());
    assertEquals(r2, resources.get(0));
  }

  private static class TestResource implements Resource {


  }

  private static class NotTestResource implements Resource {

  }
}
