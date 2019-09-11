package org.javacompet.util;

import java.util.List;

/**
 * Retrieving information about the errors for this calss is done by implementing this interface.
 */
@FunctionalInterface
public interface HasErrors {
    List<String> getErrors();
}
