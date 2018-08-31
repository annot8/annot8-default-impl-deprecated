package io.annot8.defaultimpl.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import io.annot8.common.implementations.stores.NoOpSaveCallback;
import io.annot8.core.exceptions.Annot8RuntimeException;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.content.DefaultInputStream.Builder;
import io.annot8.defaultimpl.content.DefaultInputStream.BuilderFactory;
import io.annot8.testing.testimpl.TestConstants;
import io.annot8.testing.testimpl.TestItem;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class DefaultInputStreamTest {

    @Test
    public void testBuilderFactory(){
        BuilderFactory factory = new BuilderFactory();
        assertNotNull(factory.create(new TestItem(), new NoOpSaveCallback<>()));
    }

    @Test
    public void testBuilder(){
        Builder builder = new Builder(new NoOpSaveCallback<>());
        DefaultInputStream content = null;
        try {
            content = builder.create(TestConstants.CONTENT_ID, TestConstants.CONTENT_NAME, null, () -> new ByteArrayInputStream("test".getBytes()));
        } catch (IncompleteException e) {
            fail("Test should not throw an exception here", e);
        }
        assertEquals(TestConstants.CONTENT_ID, content.getId());
        assertEquals(TestConstants.CONTENT_NAME, content.getName());
        try {
            assertEquals("test", new String(content.getData().readAllBytes()));
        } catch (IOException e) {
            fail("Test should not throw an exception here", e);
        }
    }

    @Test
    public void testNonSupplierError(){
        Builder builder = new Builder(new NoOpSaveCallback<>());
        assertThrows(Annot8RuntimeException.class, () -> builder.withData(new ByteArrayInputStream("test".getBytes())));
    }

}