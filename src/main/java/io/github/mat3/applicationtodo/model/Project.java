package io.github.mat3.applicationtodo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Project's description must be not null")
    private String description;
    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> groups;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "project")
    private Set<ProjectSteps> steps;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    Set<TaskGroup> getGroups() {
        return groups;
    }

    void setGroups(Set<TaskGroup> groups) {
        this.groups = groups;
    }

    public Set<ProjectSteps> getSteps() {
        return steps;
    }

    public void setSteps(Set<ProjectSteps> steps) {
        this.steps = steps;
    }
}
