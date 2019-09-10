package org.todos.util.func;

import java.util.function.Function;

/**
 * Function is needed to wrap checked exceptions in map lambdas.
 *
 * @param <T> - input type
 * @param <R> - returned type
 * @param <E> - thrown exception
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;

    static <T, R, E extends Throwable> Function<T, R> unchecked(final ThrowingFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (final Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}