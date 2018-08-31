package io.annot8.defaultimpl.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import io.annot8.common.implementations.stores.NoOpSaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.defaultimpl.content.SimpleFile.BuilderFactory;
import io.annot8.testing.testimpl.TestItem;
import java.io.File;
import org.junit.jupiter.api.Test;

public class SimpleFileTest {

    @Test
    public void testBuilderFactory() {
        BuilderFactory factory = new BuilderFactory();
        assertNotNull(factory.create(new TestItem(), new NoOpSaveCallback<>()));
        assertBasicBuilderUsage(factory.create(new TestItem(), new NoOpSaveCallback<>()));
        assertIncompleteBuilderUsage(factory.create(new TestItem(), new NoOpSaveCallback<>()));
    }

    private void assertIncompleteBuilderUsage(Content.Builder<SimpleFile, File> builder){
        assertThrows(IncompleteException.class, () -> builder.save());
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