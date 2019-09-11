package org.javacompet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.javacompet.util.CompilationResult;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public final class JavaCompiledClassInfo
        implements CompilationResult {

    private List<String> errors;
    private long executionTime;

}
