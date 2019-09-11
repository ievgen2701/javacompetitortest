package org.javacompet.service;

import org.javacompet.model.JavaCompiledClassInfo;
import org.javacompet.repository.TestRepository;
import org.javacompet.util.CompilationResult;
import org.javacompet.util.file.ByteCodeFileManager;
import org.javacompet.util.jclass.StringSourceJavaClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import javax.tools.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Compiles and executes the given java file in-memory.
 */
@Component
public final class JavaInMemoryFileCompiler
        implements InMemoryFileCompiler {

    private final TestRepository testRepository;

    @Autowired
    public JavaInMemoryFileCompiler(final TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public CompilationResult compile(final String filename, final byte[] fileContent) {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // this guy collects any error that appears while compiling
        final DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
        // this guy will store the compiled class, so it can be loaded later on
        final JavaFileManager fileManager = new ByteCodeFileManager(compiler.getStandardFileManager(null, null, null));
        // register a compile task for our class
        final JavaCompiler.CompilationTask task = compiler.getTask(
                new PrintWriter(System.err),
                fileManager,
                diagnosticsCollector,
                null,
                null,
                Collections.singletonList(
                        new StringSourceJavaClass(
                                filename,
                                new String(fileContent)
                        )
                )
        );
        // execute the task, so our class is getting compiled
        final boolean compiledSuccessfully = task.call();
        if (compiledSuccessfully) {
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            final List<String> errors = loadClassAndRunTests(filename, fileManager);
            stopWatch.stop();
            return new JavaCompiledClassInfo(errors, stopWatch.getTotalTimeMillis());
        } else {
            return new JavaCompiledClassInfo(
                    diagnosticsCollector.getDiagnostics().stream()
                            .map(diagnostic -> diagnostic.getMessage(Locale.getDefault()))
                            .collect(Collectors.toList()),
                    -1L
            );
        }
    }

    // todo: add work with task name
    private List<String> loadClassAndRunTests(final String nameWithoutExtension,
                                              final JavaFileManager fileManager) {
        try {
            final Class<?> clazz = fileManager.getClassLoader(StandardLocation.CLASS_PATH).loadClass(nameWithoutExtension);
            final Method method = clazz.getMethod("isTriangle", int.class, int.class, int.class);
            final List<String> errors = new ArrayList<>();

            final String[] inputRows = testRepository.inputForTask("is_this_a_triangle").split("\n");
            for (final String inputRow : inputRows) {
                final String[] inputValsAsString = inputRow.split(" ");
                final int[] inputVals = Arrays.stream(inputValsAsString)
                        .map(StringUtils::trimAllWhitespace)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                final boolean expected = inputVals[inputVals.length - 1] == 1;
                if (!((boolean) method.invoke(null, inputVals[0], inputVals[1], inputVals[2]) && expected)) {
                    errors.add(
                            String.format(
                                    "Test for %d,%d,%d input fails.", inputVals[0], inputVals[1], inputVals[2]
                            )
                    );
                }
            }
            return errors;
        } catch (final IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException | IOException e) {
            throw new RuntimeException("Test failed: ", e);
        }
    }
}
