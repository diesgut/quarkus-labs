package com.diesgut.domain.task;

import com.diesgut.domain.task.dto.CreateTaskDto;
import com.diesgut.domain.task.dto.TaskDto;
import com.diesgut.domain.task.dto.UpdateTaskDto;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface TaskService {
    Uni<TaskDto> findById(Long id);
    Uni<List<TaskDto>> listForUser();
    Uni<TaskDto> create(CreateTaskDto task);
    Uni<TaskDto> update(UpdateTaskDto task);
    Uni<Void> delete(Long id);
    Uni<Boolean> setComplete(Long id, boolean complete);
}
