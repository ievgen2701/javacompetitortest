package org.javacompet.util.file;

import org.javacompet.util.jclass.CompiledJavaClass;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File manager for managing compiled classes, so they can be used in the execution process.
 * The main idea is to keep the accepted 'files' in-memory, so they can be easily accessed for the further usage.
 */
public final class ByteCodeFileManager
        extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private final Map<String, CompiledJavaClass> compiledClassesMap = new ConcurrentHashMap<>();
    private final ClassLoader classLoader;

    public ByteCodeFileManager(final StandardJavaFileManager fileManager) {
        super(fileManager);
        this.classLoader = new CompiledClassesLoader();
    }

    @Override
    public ClassLoader getClassLoader(final Location location) {
        return classLoader;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(final Location location, final String className,
                                               final JavaFileObject.Kind kind, final FileObject sibling) throws IOException {
        return compiledClassesMap.computeIfAbsent(className, (cjc) -> new CompiledJavaClass(className));
    }

    /**
     * A custom classloader that actually performs a search of the compiled class in the map and throws if
     * there is no match for the class name.
     */
    private final class CompiledClassesLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(final String name) throws ClassNotFoundException {
            return Optional.ofNullable(compiledClassesMap.get(name))
                    .map(CompiledJavaClass::getBytes)
                    .map(bytes -> super.defineClass(name, bytes, 0, bytes.length))
                    .orElseThrow(() -> new RuntimeException(
                            "Couldn't find a class " + name
                    ));
        }
    }
}
