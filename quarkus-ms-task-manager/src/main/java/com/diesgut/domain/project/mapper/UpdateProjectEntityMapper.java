package com.diesgut.domain.project.mapper;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.project.dto.UpdateProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface UpdateProjectEntityMapper {
    void updateEntityFromDto(UpdateProjectDto dto, @MappingTarget ProjectEntity entity);
}
