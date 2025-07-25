package com.diesgut.domain.project.mapper;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.project.dto.CreateProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface CreateProjectEntityMapper {
    ProjectEntity toEntity(CreateProjectDto dto);
}
