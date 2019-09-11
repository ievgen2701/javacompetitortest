package org.javacompet.util.jclass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public final class CompiledJavaClassTest {

    @Mock
    private ByteArrayOutputStream out;

    @Test
    public void getBytesCallsConversionToByteArrayOnTheInnerOutStream() {
        new CompiledJavaClass("className", out).getBytes();
        verify(out).toByteArray();
    }

    @Test
    public void returnsInnerOutStream() {
        assertThat(
                new CompiledJavaClass("className", out).openOutputStream(),
                sameInstance(out)
        );
    }

    @Test
    public void getBytesReturnsEverythingFromTheInnerOutStream() throws IOException {
        // given
        final CompiledJavaClass javaClass = new CompiledJavaClass("className");
        // when
        javaClass.openOutputStream().write(42);
        // then
        assertThat(
                javaClass.getBytes(),
                equalTo(new byte[]{42})
        );
    }
}