package org.javacompet.util.jclass;

import javax.tools.SimpleJavaFileObject;

/**
 * Holds in-memory a content of a source java file.
 */
public final class StringSourceJavaClass
        extends SimpleJavaFileObject {

    /**
     * The source code of this "file".
     */
    private final String sourceCode;

    /**
     * Constructs a new JavaSourceFromString.
     *
     * @param className  the name of the compilation unit represented by this file object
     * @param sourceCode the source code for the compilation unit represented by this file object
     */
    public StringSourceJavaClass(final String className,
                                 final String sourceCode) {
        super(ClassUri.get(className, Kind.SOURCE), Kind.SOURCE);
        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(final boolean ignoreEncodingErrors) {
        return sourceCode;
    }
}
