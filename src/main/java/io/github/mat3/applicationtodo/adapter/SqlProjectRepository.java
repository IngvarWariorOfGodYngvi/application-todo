package io.github.mat3.applicationtodo.adapter;

import io.github.mat3.applicationtodo.model.Project;
import io.github.mat3.applicationtodo.model.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("from Project p join fetch p.steps")
    List<Project> findAll();

}
