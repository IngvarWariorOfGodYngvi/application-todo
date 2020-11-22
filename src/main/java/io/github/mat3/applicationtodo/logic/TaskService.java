package io.github.mat3.applicationtodo.logic;

import io.github.mat3.applicationtodo.model.Task;
import io.github.mat3.applicationtodo.model.TaskRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {

    private final TaskRepository repository;


    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<List<Task>> findAllAsync() {
        return CompletableFuture.supplyAsync(repository::findAll);
    }
}
