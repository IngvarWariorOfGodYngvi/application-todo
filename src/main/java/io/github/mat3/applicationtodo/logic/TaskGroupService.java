package io.github.mat3.applicationtodo.logic;

import io.github.mat3.applicationtodo.model.TaskGroup;
import io.github.mat3.applicationtodo.model.TaskGroupRepository;
import io.github.mat3.applicationtodo.model.TaskRepository;
import io.github.mat3.applicationtodo.model.projection.GroupReadModel;
import io.github.mat3.applicationtodo.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;


public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    TaskGroupService(final TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given ID not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
