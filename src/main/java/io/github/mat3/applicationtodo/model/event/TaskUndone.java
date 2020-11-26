package io.github.mat3.applicationtodo.model.event;

import io.github.mat3.applicationtodo.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent {
    TaskUndone(Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
