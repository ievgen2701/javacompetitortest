package org.javacompet.util.file;

/**
 * Different kind of checks can be performed within the class by implementing this interface.
 */
@FunctionalInterface
public interface FileChecker {

    /**
     * Checks whether file has an expected extension.
     *
     * @param filename - file name to check
     * @return true if extension is expected, false otherwise.
     */
    boolean hasCorrectExtension(String filename);

}
