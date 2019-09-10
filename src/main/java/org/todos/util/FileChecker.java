package org.todos.util;

@FunctionalInterface
public interface FileChecker {

    boolean hasCorrectExtension(String filename);

}
