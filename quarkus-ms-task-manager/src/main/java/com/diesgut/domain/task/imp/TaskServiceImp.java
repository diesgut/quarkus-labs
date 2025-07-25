package com.diesgut.domain.task.imp;

import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.user.UserService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@ApplicationScoped
public class TaskServiceImp {
    private final UserService userService;

    public Uni<TaskEntity> findById(Long id) {
        return userService.getCurrentUser()
                .chain(user -> TaskEntity.<TaskEntity>findById(id)
                        .onItem().ifNull().failWith(() -> new ObjectNotFoundException(id, "Task"))
                        .onItem().invoke(task -> {
                            if (!user.equals(task.userEntity)) {
                                throw new UnauthorizedException("You are not allowed to update this task");
                            }
                        }));
    }

    public Uni<List<TaskEntity>> listForUser() {
        return userService.getCurrentUser()
                .chain(user -> TaskEntity.find("userEntity", user).list());
    }

    @WithTransaction
    public Uni<TaskEntity> create(TaskEntity task) {
        return userService.getCurrentUser()
                .chain(user -> {
                    task.userEntity = user;
                    return task.persistAndFlush();
                });
    }

    @WithTransaction
    public Uni<TaskEntity> update(TaskEntity task) {
        return findById(task.id)
                .chain(t -> TaskEntity.getSession())
                .chain(s -> s.merge(task));
    }

    @WithTransaction
    public Uni<Void> delete(Long id) {
        return findById(id)
                .chain(TaskEntity::delete);
    }

    @WithTransaction
    public Uni<Boolean> setComplete(Long id, boolean complete) {
        return findById(id)
                .chain(task -> {
                    task.complete_at = complete ? ZonedDateTime.now() : null;
                    return task.persistAndFlush();
                })
                .chain(task -> Uni.createFrom().item(complete));
    }
}
