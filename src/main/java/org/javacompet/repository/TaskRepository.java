package org.javacompet.repository;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Repository
public class TaskRepository {

    private final Resource[] resources;

    @Autowired
    public TaskRepository(final ResourceLoader resourceLoader) throws IOException {
        resources = new PathMatchingResourcePatternResolver(resourceLoader)
                .getResources("classpath*:/**/tasks/*.txt");
    }

    // todo: add more tasks to /tasks folder in the resource folder
    public String randomTask() throws IOException {
        return FileUtils.readFileToString(
                resources[new Random().nextInt(resources.length)].getFile(),
                StandardCharsets.UTF_8
        );
    }
}
