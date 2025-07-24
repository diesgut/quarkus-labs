package com.diesgut.domain.project.imp;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.project.ProjectService;
import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.user.UserService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@ApplicationScoped
public class ProjectServiceImp implements ProjectService {
    private final UserService userService;

    public Uni<ProjectEntity> findById(Long id) {
        return userService.getCurrentUser()
                .chain(user -> ProjectEntity.<ProjectEntity>findById(id)
                        .onItem().ifNull().failWith(() -> new ObjectNotFoundException(id, "Project"))
                        .onItem().invoke(project -> {
                            if (!user.equals(project.getUserEntity())) {
                                throw new UnauthorizedException("You are not allowed to update this project");
                            }
                        }));
    }

    public Uni<List<ProjectEntity>> listForUser() {
        return userService.getCurrentUser()
                .chain(user -> ProjectEntity.find("userEntity", user).list());
    }

    @WithTransaction
    public Uni<ProjectEntity> create(ProjectEntity project) {
        return userService.getCurrentUser()
                .chain(user -> {
                    project.setUserEntity(user);
                    return project.persistAndFlush();
                });
    }

    @WithTransaction
    public Uni<ProjectEntity> update(ProjectEntity project) {
        return findById(project.getId())
                .chain(p -> ProjectEntity.getSession())
                .chain(s -> s.merge(project));
    }

    @WithTransaction
    public Uni<Void> delete(Long id) {
        return findById(id)
                .chain(p -> TaskEntity.update("projectEntity = null where projectEntity = ?1", p)
                        .chain(i -> p.delete()));
    }
}
