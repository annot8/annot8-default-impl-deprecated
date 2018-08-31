package io.annot8.defaultimpl.content;

import io.annot8.common.implementations.stores.NoOpSaveCallback;
import io.annot8.testing.testimpl.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultTextTest {
  
      @Test
      public void testBuilder(){
          DefaultText.Builder builder = new DefaultText.Builder(new NoOpSaveCallback<>());
          DefaultText defaultText = builder.create(TestConstants.CONTENT_ID, TestConstants.CONTENT_NAME, null, () -> "test");
          Assertions.assertEquals(TestConstants.CONTENT_ID, defaultText.getId());
          Assertions.assertEquals(TestConstants.CONTENT_NAME, defaultText.getName());
          Assertions.assertEquals("test", defaultText.getData());
      }
  
  }