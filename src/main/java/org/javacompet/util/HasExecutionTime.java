package org.javacompet.util;

/**
 * If class has any measurements regarding execution time it should implement this interface.
 */
@FunctionalInterface
public interface HasExecutionTime {
    long getExecutionTime();
}
