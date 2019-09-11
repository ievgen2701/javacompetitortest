package org.javacompet.util.func;

import java.util.function.Consumer;

/**
 * Consumer is needed to wrap checked exceptions in consumer lambdas.
 *
 * @param <T> - acceptable object
 * @param <E> - thrown exception
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> {
    void accept(T t) throws E;

    static <T, E extends Throwable> Consumer<T> unchecked(final ThrowingConsumer<T, E> consumer) {
        return (t) -> {
            try {
                consumer.accept(t);
            } catch (final Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}