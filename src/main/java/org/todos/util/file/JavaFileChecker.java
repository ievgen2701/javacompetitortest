package org.todos.util.file;

import org.springframework.stereotype.Component;

import javax.tools.JavaFileObject;
import java.util.Optional;

/**
 * One of the implementations of the {@link FileChecker} that is working with {@code .java} files.
 */
@Component
public final class JavaFileChecker
        implements FileChecker {

    @Override
    public boolean hasCorrectExtension(final String filename) {
        return Optional.ofNullable(filename)
                .map(name -> name.endsWith(JavaFileObject.Kind.SOURCE.extension))
                .orElse(false);
    }
}
