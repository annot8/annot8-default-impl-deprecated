package io.annot8.defaultimpl.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import io.annot8.core.components.Capabilities;
import io.annot8.core.components.Resource;
import io.annot8.core.settings.Settings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class SimpleContextTest {

  @Test
  public void testSimpleContextDefault() {
    Resource r1 = mock(Resource.class);
    Resource r2 = mock(TestResource.class);

    SimpleContext context = new SimpleContext();
    context.addResource("resource1", r1);
    context.addResource("resource2", r2);

    assertFalse(context.getSettings().isPresent());

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
    Resource r2 = mock(TestResource.class);

    Map<String, Resource> r = new HashMap<>();
    r.put("resource1", r1);
    r.put("resource2", r2);

    SimpleContext context = new SimpleContext(r);

    assertFalse(context.getSettings().isPresent());

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
  public void testSimpleContextSettings() {
    Settings s = mock(Settings.class);

    SimpleContext context = new SimpleContext(s);

    assertTrue(context.getSettings().isPresent());
    assertEquals(s, context.getSettings().get());

    List<String> keys = context.getResourceKeys().collect(Collectors.toList());
    assertTrue(keys.isEmpty());

    List<Resource> resources = context.getResources(Resource.class).collect(Collectors.toList());
    assertTrue(resources.isEmpty());
  }

  @Test
  public void testSimpleContextSettingsAndMap() {
    Resource r1 = mock(Resource.class);
    Resource r2 = mock(TestResource.class);
    Settings s = mock(Settings.class);

    Map<String, Resource> r = new HashMap<>();
    r.put("resource1", r1);
    r.put("resource2", r2);

    SimpleContext context = new SimpleContext(s, r);

    assertTrue(context.getSettings().isPresent());
    assertEquals(s, context.getSettings().get());

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

    @Override
    public Capabilities getCapabilities(Settings settings) {
      return mock(Capabilities.class);
    }
  }
}
