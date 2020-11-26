package io.github.mat3.applicationtodo.model.event;

import io.github.mat3.applicationtodo.model.Task;

import java.time.Clock;

public class TaskDone extends TaskEvent {
     TaskDone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
