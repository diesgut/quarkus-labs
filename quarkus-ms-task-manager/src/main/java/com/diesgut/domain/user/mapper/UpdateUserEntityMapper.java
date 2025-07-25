package com.diesgut.domain.user.mapper;

import com.diesgut.domain.user.UserEntity;
import com.diesgut.domain.user.dto.CreateUserDto;
import com.diesgut.domain.user.dto.UpdateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface UpdateUserEntityMapper {
    void updateEntityFromDto(UpdateUserDto dto, @MappingTarget UserEntity entity);
}
