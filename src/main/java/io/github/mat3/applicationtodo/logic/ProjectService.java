package io.github.mat3.applicationtodo.logic;

import io.github.mat3.applicationtodo.TaskConfigurationProperties;
import io.github.mat3.applicationtodo.model.Project;
import io.github.mat3.applicationtodo.model.ProjectRepository;
import io.github.mat3.applicationtodo.model.TaskGroupRepository;
import io.github.mat3.applicationtodo.model.projection.GroupReadModel;
import io.github.mat3.applicationtodo.model.projection.GroupTaskWriteModel;
import io.github.mat3.applicationtodo.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService service;
    private TaskConfigurationProperties config;

    public ProjectService(ProjectRepository repository, TaskGroupRepository taskGroupRepository, TaskGroupService service, TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.service = service;
        this.config = config;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project save(Project toSave) {
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {

        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(project.getSteps().stream()
                            .map(projectSteps -> {
                                var task = new GroupTaskWriteModel();
                                task.setDescription(projectSteps.getDescription());
                                task.setDeadline(deadline.plusDays(projectSteps.getDaysToDeadline()));
                                return task;
                            })
                            .collect(Collectors.toSet())
                    );
                    return service.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given ID not found"));
    }
}
