package com.diesgut.domain.task.mapper;

import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.task.dto.CreateTaskDto;
import com.diesgut.domain.task.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface CreateTaskEntityMapper {
    @Mapping(target = "projectEntity.id", source = "projectId")
    TaskEntity toEntity(CreateTaskDto dto);
}
