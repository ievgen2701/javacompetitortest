package org.todos.util.jclass;

import org.todos.util.HasBytes;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * Holds in-memory a compiled java class.
 */
public final class CompiledJavaClass
        extends SimpleJavaFileObject
        implements HasBytes {

    private final ByteArrayOutputStream out;

    CompiledJavaClass(final String className,
                      final ByteArrayOutputStream out) {
        super(ClassUri.get(className, Kind.CLASS), Kind.CLASS);
        this.out = out;
    }

    public CompiledJavaClass(final String className) {
        this(className, new ByteArrayOutputStream());
    }

    @Override
    public byte[] getBytes() {
        return out.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() {
        return out;
    }
}
