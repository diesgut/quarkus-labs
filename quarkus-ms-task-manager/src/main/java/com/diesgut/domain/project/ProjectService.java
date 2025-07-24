package com.diesgut.domain.project;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ProjectService {
    Uni<ProjectEntity> findById(Long id);
    Uni<List<ProjectEntity>> listForUser();
    Uni<ProjectEntity> create(ProjectEntity project);
    Uni<ProjectEntity> update(ProjectEntity project);
    Uni<Void> delete(Long id);
}
