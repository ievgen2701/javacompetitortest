package org.todos.util;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * Holds in-memory a compiled java class.
 */
public final class CompiledJavaClass
        extends SimpleJavaFileObject {

    private final ByteArrayOutputStream out;

    public CompiledJavaClass(final String className,
                             final ByteArrayOutputStream out) {
        super(ClassUri.get(className, Kind.CLASS), Kind.CLASS);
        this.out = out;
    }

    public CompiledJavaClass(final String className) {
        this(className, new ByteArrayOutputStream());
    }

    public byte[] getBytes() {
        return out.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() {
        return out;
    }
}
