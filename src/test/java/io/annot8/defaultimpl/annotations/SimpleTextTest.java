package io.annot8.defaultimpl.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.annot8.common.implementations.stores.NoOpSaveCallback;
import io.annot8.defaultimpl.content.SimpleText;
import io.annot8.defaultimpl.content.SimpleText.Builder;
import io.annot8.testing.testimpl.TestConstants;
import org.junit.jupiter.api.Test;

public class SimpleTextTest {

    @Test
    public void testBuilder(){
        Builder builder = new Builder(new NoOpSaveCallback<>());
        SimpleText simpleText = builder.create(TestConstants.CONTENT_ID, TestConstants.CONTENT_NAME, null, () -> "test");
        assertEquals(TestConstants.CONTENT_ID, simpleText.getId());
        assertEquals(TestConstants.CONTENT_NAME, simpleText.getName());
        assertEquals("test", simpleText.getData());
    }

}
