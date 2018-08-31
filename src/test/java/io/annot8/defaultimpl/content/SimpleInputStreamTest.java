package io.annot8.defaultimpl.content;

import io.annot8.common.implementations.stores.NoOpSaveCallback;
import io.annot8.core.exceptions.Annot8RuntimeException;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.content.SimpleInputStream.Builder;
import io.annot8.defaultimpl.content.SimpleInputStream.BuilderFactory;
import io.annot8.defaultimpl.stores.SimpleAnnotationStore.SimpleAnnotationStoreFactory;
import io.annot8.testing.testimpl.TestConstants;
import io.annot8.testing.testimpl.TestItem;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleInputStreamTest {

    @Test
    public void testBuilderFactory(){
        BuilderFactory factory = new BuilderFactory(new SimpleAnnotationStoreFactory());
        assertNotNull(factory.create(new TestItem(), new NoOpSaveCallback<>()));
    }

    @Test
    public void testBuilder(){
        Builder builder = new Builder(new SimpleAnnotationStoreFactory(), new NoOpSaveCallback<>());
        SimpleInputStream content = null;
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
        Builder builder = new Builder(new SimpleAnnotationStoreFactory(), new NoOpSaveCallback<>());
        assertThrows(Annot8RuntimeException.class, () -> builder.withData(new ByteArrayInputStream("test".getBytes())));
    }

}