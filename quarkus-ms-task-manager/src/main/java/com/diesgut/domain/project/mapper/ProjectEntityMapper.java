package com.diesgut.domain.project.mapper;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.project.dto.ProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface ProjectEntityMapper {
    @Mapping(target = "createdAt", source = "created_at")
    @Mapping(target = "updatedAt", source = "updated_at")
    ProjectDto toDto(ProjectEntity entity);

    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    ProjectEntity toEntity(ProjectDto dto);
}
