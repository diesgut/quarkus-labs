package com.diesgut.domain.task.mapper;

import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.task.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface TaskEntityMapper {
    @Mapping(target = "userId", source = "userEntity.id")
    @Mapping(target = "projectId", source = "projectEntity.id")
    @Mapping(target = "completeAt", source = "complete_at")
    @Mapping(target = "createdAt", source = "created_at")
    @Mapping(target = "updatedAt", source = "updated_at")
    TaskDto toDto(TaskEntity entity);

    @Mapping(target = "userEntity.id", source = "userId")
    @Mapping(target = "projectEntity.id", source = "projectId")
    @Mapping(target = "complete_at", source = "completeAt")
    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    TaskEntity toEntity(TaskDto entity);
}
