package com.diesgut.domain.task.imp;

import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.task.TaskService;
import com.diesgut.domain.task.dto.CreateTaskDto;
import com.diesgut.domain.task.dto.UpdateTaskDto;
import com.diesgut.domain.task.mapper.CreateTaskEntityMapper;
import com.diesgut.domain.task.mapper.TaskEntityMapper;
import com.diesgut.domain.task.dto.TaskDto;
import com.diesgut.domain.task.mapper.UpdateTaskEntityMapper;
import com.diesgut.domain.user.UserService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@WithSession
@ApplicationScoped
public class TaskServiceImp implements TaskService {
    private final UserService userService;
    private final TaskEntityMapper taskEntityMapper;
    private final CreateTaskEntityMapper createTaskEntityMapper;
    private final UpdateTaskEntityMapper updateTaskEntityMapper;

    public Uni<TaskDto> findById(Long id) {
        return userService.getCurrentUser()
                .chain(user -> findEntityById(id)
                        .onItem().invoke(task -> {
                            if (!user.equals(task.userEntity)) {
                                throw new UnauthorizedException("You are not allowed to update this task");
                            }
                        }).map(taskEntityMapper::toDto));
    }

    private Uni<TaskEntity> findEntityById(Long id) {
        return TaskEntity.<TaskEntity>findById(id)
                .onItem()
                .ifNull() // If the user is not found, return null
                .failWith(() -> new ObjectNotFoundException(id, "Task"));
    }

    public Uni<List<TaskDto>> listForUser() {
        return userService.getCurrentUser()
                .chain(user -> TaskEntity.<TaskEntity>find("userEntity", user).list())
                .onItem().transform(entityList ->
                        entityList.stream()
                                .map(taskEntityMapper::toDto)
                                .toList()
                );
    }

    @WithTransaction
    public Uni<TaskDto> create(CreateTaskDto task) {
        return userService.getCurrentUser()
                .chain(user -> {
                    TaskEntity taskEntity = createTaskEntityMapper.toEntity(task);
                    taskEntity.userEntity = user;
                    return taskEntity.<TaskEntity>persistAndFlush();
                }).onItem().transform(taskEntityMapper::toDto);
    }

    @WithTransaction
    public Uni<TaskDto> update(UpdateTaskDto task) {
        return findEntityById(task.getId())
                // 1. Usa .chain() porque la operación interna devuelve un Uni
                .chain(projectEntity -> {
                    updateTaskEntityMapper.updateEntityFromDto(task, projectEntity);
                    return projectEntity.<TaskEntity>persistAndFlush();
                })
                // 2. Usa .transform() para la conversión final a DTO
                .onItem().transform(taskEntityMapper::toDto);
    }

    @WithTransaction
    public Uni<Void> delete(Long id) {
        return findEntityById(id)
                .chain(TaskEntity::delete);
    }

    @WithTransaction
    public Uni<Boolean> setComplete(Long id, boolean complete) {
        return findEntityById(id)
                .chain(task -> {
                    task.complete_at = complete ? ZonedDateTime.now(ZoneOffset.UTC) : null;
                    return task.<TaskEntity>persistAndFlush();
                })
                .chain(task -> Uni.createFrom().item(complete));
    }
}
