package io.github.mat3.applicationtodo.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface PersistedTaskEventRepository extends JpaRepository<PersistedTaskEvent,Integer> {
    List<PersistedTaskEvent> findByTaskId(int taskId);
}
