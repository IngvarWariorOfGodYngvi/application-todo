package io.github.mat3.applicationtodo.logic;

import io.github.mat3.applicationtodo.model.Task;
import io.github.mat3.applicationtodo.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {
    public static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repository;


    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<List<Task>> findAllAsync() {
        logger.info("Supply Async!");
        return CompletableFuture.supplyAsync(repository::findAll);

    }
}
