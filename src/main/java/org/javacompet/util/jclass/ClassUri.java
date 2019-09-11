package org.javacompet.util.jclass;

import lombok.experimental.UtilityClass;

import javax.tools.JavaFileObject;
import java.net.URI;

@UtilityClass
final class ClassUri {

    static URI get(final String className,
                   final JavaFileObject.Kind kind) {
        return URI.create("string:///" + className.replace(".", "/") + kind.extension);
    }
}
