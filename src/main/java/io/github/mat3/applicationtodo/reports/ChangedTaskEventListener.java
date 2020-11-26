package io.github.mat3.applicationtodo.reports;

import io.github.mat3.applicationtodo.model.event.TaskDone;
import io.github.mat3.applicationtodo.model.event.TaskEvent;
import io.github.mat3.applicationtodo.model.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class ChangedTaskEventListener {
    private static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    private final PersistedTaskEventRepository repository;

    ChangedTaskEventListener(PersistedTaskEventRepository repository) {
        this.repository = repository;
    }


    @EventListener
    public void on(TaskDone event) {
        onChanged(event);
    }


    @EventListener
    public void on(TaskUndone event) {
        onChanged(event);

    }

    private void onChanged(TaskEvent event) {
        logger.info("Got " + event);
        repository.save(new PersistedTaskEvent(event));
    }
}
