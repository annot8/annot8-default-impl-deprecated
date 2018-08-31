package io.annot8.defaultimpl.annotations;

import io.annot8.common.implementations.stores.NoOpSaveCallback;
import io.annot8.defaultimpl.content.SimpleText;
import io.annot8.defaultimpl.content.SimpleText.Builder;
import io.annot8.defaultimpl.content.SimpleText.BuilderFactory;
import io.annot8.defaultimpl.stores.SimpleAnnotationStore.SimpleAnnotationStoreFactory;
import io.annot8.testing.testimpl.TestConstants;
import io.annot8.testing.testimpl.TestItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleTextTest {

    @Test
    public void testBuilderFactory(){
        BuilderFactory factory = new BuilderFactory(new SimpleAnnotationStoreFactory());
        assertNotNull(factory.create(new TestItem(), new NoOpSaveCallback<>()));
    }

    @Test
    public void testBuilder(){
        Builder builder = new Builder(new SimpleAnnotationStoreFactory(), new NoOpSaveCallback<>());
        SimpleText simpleText = builder.create(TestConstants.CONTENT_ID, TestConstants.CONTENT_NAME, null, () -> "test");
        assertEquals(TestConstants.CONTENT_ID, simpleText.getId());
        assertEquals(TestConstants.CONTENT_NAME, simpleText.getName());
        assertEquals("test", simpleText.getData());
    }

}
