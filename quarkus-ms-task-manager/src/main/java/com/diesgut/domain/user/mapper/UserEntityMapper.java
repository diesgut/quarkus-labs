package com.diesgut.domain.user.mapper;

import com.diesgut.domain.user.UserEntity;
import com.diesgut.domain.user.dto.CreateUserDto;
import com.diesgut.domain.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface UserEntityMapper {
    @Mapping(target = "createdAt", source = "created_at")
    @Mapping(target = "updatedAt", source = "updated_at")
    UserDto toDto(UserEntity entity);

    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    UserEntity toEntity(UserDto entity);
}
