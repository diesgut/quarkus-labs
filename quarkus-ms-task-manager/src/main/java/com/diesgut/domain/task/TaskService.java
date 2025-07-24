package com.diesgut.domain.task;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface TaskService {
    Uni<TaskEntity> findById(Long id);
    Uni<List<TaskEntity>> listForUser();
    Uni<TaskEntity> create(TaskEntity task);
    Uni<TaskEntity> update(TaskEntity task);
    Uni<Void> delete(Long id);
    Uni<Boolean> setComplete(Long id, boolean complete);
}
