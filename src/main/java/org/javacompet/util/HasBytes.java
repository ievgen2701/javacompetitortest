package org.javacompet.util;

/**
 * Byte conversion of the class data is done by implementing this interface.
 */
@FunctionalInterface
public interface HasBytes {
    byte[] getBytes();
}
