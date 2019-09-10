package org.todos.util;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class JavaFileChecker
        implements FileChecker {

    private static final String JAVA_EXTENSION = ".java";

    @Override
    public boolean hasCorrectExtension(final String filename) {
        return Optional.ofNullable(filename)
                .map(name -> name.endsWith(JAVA_EXTENSION))
                .orElse(false);
    }
}
