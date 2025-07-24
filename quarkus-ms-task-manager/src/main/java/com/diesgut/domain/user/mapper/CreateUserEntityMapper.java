package com.diesgut.domain.user.mapper;

import com.diesgut.domain.user.UserEntity;
import com.diesgut.domain.user.dto.CreateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface CreateUserEntityMapper {
    CreateUserDto toDto(UserEntity entity);

    UserEntity toEntity(CreateUserDto entity);
}
