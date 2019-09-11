package org.javacompet.service;

import org.javacompet.util.CompilationResult;

/**
 * Compiling of a clss in-memory is enabled by implementing this interface.
 */
@FunctionalInterface
public interface InMemoryFileCompiler {

    /**
     * Compiles the given file content.
     *
     * @param filename    - file name fo the file that was uploaded and needed to be compiled.
     * @param fileContent - file's content
     * @return an information about the compilation
     */
    CompilationResult compile(String filename, byte[] fileContent);

}
