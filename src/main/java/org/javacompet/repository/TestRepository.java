package org.javacompet.repository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Repository
public class TestRepository {

    private final Resource[] resources;

    @Autowired
    public TestRepository(final ResourceLoader resourceLoader) throws IOException {
        resources = new PathMatchingResourcePatternResolver(resourceLoader)
                .getResources("classpath*:/**/inputs/*.txt");
    }

    // todo: add more inputs for tasks and indexing to /inputs folder in the resource folder
    public String inputForTask(final String taskName) throws IOException {
        final Resource resource = Arrays.stream(resources)
                .filter(res -> taskName.equals(FilenameUtils.removeExtension(res.getFilename())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find inputs for the given task: " + taskName));
        return FileUtils.readFileToString(resource.getFile(), StandardCharsets.UTF_8);
    }
}
