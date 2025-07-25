package com.diesgut.domain.project;

import com.diesgut.domain.project.dto.CreateProjectDto;
import com.diesgut.domain.project.dto.ProjectDto;
import com.diesgut.domain.project.dto.UpdateProjectDto;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ProjectService {
    Uni<ProjectDto> findById(Long id);
    Uni<List<ProjectDto>> listForUser();
    Uni<ProjectDto> create(CreateProjectDto project);
    Uni<ProjectDto> update(UpdateProjectDto project);
    Uni<Void> delete(Long id);
}
