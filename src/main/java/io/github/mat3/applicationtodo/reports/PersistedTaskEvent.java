package io.github.mat3.applicationtodo.reports;


import io.github.mat3.applicationtodo.model.event.TaskEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table("task_events")
class PersistedTaskEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int taskId;
    String name;
    LocalDateTime occurrence;

    public PersistedTaskEvent() {
    }


    public PersistedTaskEvent(TaskEvent source) {
        taskId = source.getTaskId();
        name = source.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }
}