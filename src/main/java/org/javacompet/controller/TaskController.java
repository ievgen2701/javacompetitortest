package org.javacompet.controller;

import org.javacompet.model.JavaClassInfo;
import org.javacompet.repository.TaskRepository;
import org.javacompet.service.InMemoryFileCompiler;
import org.javacompet.util.CompilationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Receives submitted java files and delegates them to executor.
 */
// todo: add integration test
@RestController
@RequestMapping("/tasks")
public final class TaskController {

    private final InMemoryFileCompiler compiler;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(final InMemoryFileCompiler compiler,
                          final TaskRepository taskRepository) {
        this.compiler = compiler;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ModelAndView initialize() throws IOException {
        final ModelAndView mav = new ModelAndView("main");
        mav.addObject("javaClassInfo", new JavaClassInfo());
        return mav;
    }

    @ModelAttribute("taskDescription")
    public String taskDescription() throws IOException {
        return taskRepository.randomTask();
    }

    @PostMapping
    public ModelAndView handleTask(@ModelAttribute final JavaClassInfo javaClassInfo) {
        final ModelAndView mav = new ModelAndView("main");

        // todo: extract to separate class
        final String javaSource = javaClassInfo.getSourceCode();
        if (javaSource.isEmpty()) {
            mav.addObject("result", "Please provide the solution.");
        } else {
            final int classNameIndex = javaSource.indexOf("class");
            final int firstCurlyBracer = javaSource.indexOf('{');
            if (classNameIndex == -1 || firstCurlyBracer == -1) {
                mav.addObject("result", "Invalid java class has been provided.");
            } else {
                final String className = javaSource.substring(classNameIndex + 5, firstCurlyBracer).trim();

                final CompilationResult compile = compiler.compile(className, javaSource.getBytes());

                mav.addObject(
                        "result",
                        // todo: extract to separate class
                        new StringBuilder(compile.getExecutionTime() <= 0L ? "" : "Execution time: " + compile.getExecutionTime() + " ms.")
                                .append("\n")
                                .append(compile.getErrors().isEmpty() ? "" : compile.getErrors().stream().collect(Collectors.joining("\n")))
                );
            }
        }

        return mav;
    }

    @GetMapping("/random")
    public ModelAndView getRandomTask() throws IOException {
        final ModelAndView mav = new ModelAndView("main");
        mav.addObject("taskDescription", taskRepository.randomTask());
        return mav;
    }

}
