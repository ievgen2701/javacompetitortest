package org.javacompet.util.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

@RunWith(MockitoJUnitRunner.class)
public final class ByteCodeFileManagerTest {

    @Mock
    private StandardJavaFileManager fileManager;

    @Test(expected = RuntimeException.class)
    public void classLoaderWillFailToFindClassIfMapIsEmpty() throws ClassNotFoundException {
        new ByteCodeFileManager(fileManager)
                .getClassLoader(null)
                .loadClass("className");
    }

    @Test
    public void getJavaFileForOutputReturnsTheSameForTheSameClassName() throws IOException {
        final JavaFileManager javaFileManager =
                new ByteCodeFileManager(fileManager);
        assertThat(
                javaFileManager.getJavaFileForOutput(
                        StandardLocation.CLASS_OUTPUT,
                        "className",
                        JavaFileObject.Kind.CLASS,
                        null
                ),
                // repeat the action for the same class name,
                // so we get like two records under the same key
                sameInstance(javaFileManager.getJavaFileForOutput(
                        StandardLocation.CLASS_OUTPUT,
                        "className",
                        JavaFileObject.Kind.CLASS,
                        null
                ))
        );
    }
}