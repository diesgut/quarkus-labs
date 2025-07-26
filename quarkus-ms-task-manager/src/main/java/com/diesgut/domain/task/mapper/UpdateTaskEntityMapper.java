package com.diesgut.domain.task.mapper;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.project.dto.UpdateProjectDto;
import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.task.dto.UpdateTaskDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface UpdateTaskEntityMapper {
    void updateEntityFromDto(UpdateTaskDto dto, @MappingTarget TaskEntity entity);
}
