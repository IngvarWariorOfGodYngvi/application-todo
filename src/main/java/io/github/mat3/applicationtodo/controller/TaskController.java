package io.github.mat3.applicationtodo.controller;

import io.github.mat3.applicationtodo.model.Task;
import io.github.mat3.applicationtodo.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    public TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

}
