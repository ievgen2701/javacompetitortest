package org.todos.util.jclass;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class StringSourceJavaClassTest {

    @Test
    public void getCharContentReturnsTheSourceCodeItself() {
        assertThat(
                new StringSourceJavaClass("className", "some source code")
                        .getCharContent(false),
                is("some source code")
        );
    }
}