package com.diesgut.domain.project.imp;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.project.ProjectEntityMapper;
import com.diesgut.domain.project.ProjectService;
import com.diesgut.domain.project.dto.CreateProjectDto;
import com.diesgut.domain.project.dto.ProjectDto;
import com.diesgut.domain.project.dto.UpdateProjectDto;
import com.diesgut.domain.project.mapper.CreateProjectEntityMapper;
import com.diesgut.domain.project.mapper.UpdateProjectEntityMapper;
import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.user.UserService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@WithSession
@ApplicationScoped
public class ProjectServiceImp implements ProjectService {
    private final UserService userService;
    private final ProjectEntityMapper projectEntityMapper;
    private final CreateProjectEntityMapper createProjectEntityMapper;
    private final UpdateProjectEntityMapper updateProjectEntityMapper;

    public Uni<ProjectDto> findById(Long id) {
        return findEntityById(id)
                .onItem()
                .transform(projectEntityMapper::toDto);
    }

    private Uni<ProjectEntity> findEntityById(Long id) {
        return userService.getCurrentUser()
                .chain(user -> ProjectEntity.<ProjectEntity>findById(id)
                        .onItem().ifNull().failWith(() -> new ObjectNotFoundException(id, "Project"))
                        .onItem().invoke(project -> {
                            if (!user.equals(project.getUserEntity())) {
                                throw new UnauthorizedException("You are not allowed to update this project");
                            }
                        }));
    }

    public Uni<List<ProjectDto>> listForUser() {
        return userService.getCurrentUser()
                .chain(user -> ProjectEntity.find("userEntity", user).list())
                .onItem().transform(entityList ->
                        entityList.stream()
                                .map(projectEntity -> projectEntityMapper.toDto((ProjectEntity) projectEntity))
                                .toList()
                );
    }

    @WithTransaction
    public Uni<ProjectDto> create(CreateProjectDto project) {
        return userService.getCurrentUser()
                .chain(user -> {
                    ProjectEntity projectEntity = createProjectEntityMapper.toEntity(project);
                    projectEntity.setUserEntity(user);
                    return projectEntity.persistAndFlush();
                }).onItem().transform(projectEntitySaved -> {
                    return projectEntityMapper.toDto((ProjectEntity) projectEntitySaved);
                });
    }

    @WithTransaction
    public Uni<ProjectDto> update(UpdateProjectDto project) {
        return findEntityById(project.getId())
                // 1. Usa .chain() porque la operación interna devuelve un Uni
                .chain(projectEntity -> {
                    updateProjectEntityMapper.updateEntityFromDto(project, projectEntity);
                    return projectEntity.persistAndFlush();
                })
                // 2. Usa .transform() para la conversión final a DTO
                .onItem().transform(projectEntityUpdated -> {
                    return projectEntityMapper.toDto((ProjectEntity) projectEntityUpdated);
                });
    }

    @WithTransaction
    public Uni<Void> delete(Long id) {
        return findEntityById(id)
                .chain(projectEntity -> TaskEntity.update("projectEntity = null where projectEntity = ?1", projectEntity)
                        .chain(i -> projectEntity.delete()));
    }
}
