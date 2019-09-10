package org.todos.util;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class JavaFileCheckerTest {

    @Test
    public void hasCorrectExtensionReturnsTrueForJavaFiles() {
        assertThat(
                new JavaFileChecker().hasCorrectExtension("test.java"),
                is(true)
        );
    }

    @Test
    public void hasCorrectExtensionReturnsFalseForAllNonJavaFiles() {
        assertThat(
                new JavaFileChecker().hasCorrectExtension("any.nonjava.file"),
                is(false)
        );
    }

    @Test
    public void hasCorrectExtensionReturnsFalseOnNulls() {
        assertThat(
                new JavaFileChecker().hasCorrectExtension(null),
                is(false)
        );
    }
}