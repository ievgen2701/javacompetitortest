package org.todos.util;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class ClassUriTest {

    @Test
    public void uriForStringResourceIsGenerated() {
        assertThat(
                ClassUri.get("Test", JavaFileObject.Kind.SOURCE).toString(),
                is("string:///Test.java")
        );
    }

    @Test
    public void everyDotWillBeReplacedWithSlashSign() {
        assertThat(
                ClassUri.get("org.test.Test", JavaFileObject.Kind.SOURCE).toString(),
                is("string:///org/test/Test.java")
        );
    }
}