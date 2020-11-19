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
}
