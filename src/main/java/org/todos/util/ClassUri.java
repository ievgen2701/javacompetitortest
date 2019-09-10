package org.todos.util;

import lombok.experimental.UtilityClass;

import javax.tools.JavaFileObject;
import java.net.URI;

@UtilityClass
public final class ClassUri {

    public static URI get(final String className,
                          final JavaFileObject.Kind kind) {
        return URI.create("string:///" + className.replace(".", "/") + kind.extension);
    }
}
