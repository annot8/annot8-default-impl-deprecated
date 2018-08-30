package io.annot8.defaultimpl.content;

import io.annot8.common.implementations.stores.NoOpSaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.stores.SimpleAnnotationStore;
import io.annot8.defaultimpl.content.SimpleFile.BuilderFactory;
import io.annot8.testing.testimpl.TestItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class SimpleFileTest {

    @Test
    public void testBuilderFactory() {
        BuilderFactory factory = new BuilderFactory(SimpleAnnotationStore.factory());
        Content.Builder<SimpleFile, File> builder = factory.create(new TestItem(), new NoOpSaveCallback<>());
        assertNotNull(builder);
        assertBasicBuilderUsage(builder);
    }

    private void assertBasicBuilderUsage(Content.Builder<SimpleFile, File> builder){
        Content<File> content = null;
        try {
            content = builder.withName("test").withData(new File("test")).save();
        } catch (IncompleteException e) {
            fail("Exception not expected here", e);
        }
        assertEquals("test", content.getName());
        assertEquals(new File("test"), content.getData());
    }

}