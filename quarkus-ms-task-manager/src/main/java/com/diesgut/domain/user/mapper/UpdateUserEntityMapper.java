package com.diesgut.domain.user.mapper;

import com.diesgut.domain.user.UserEntity;
import com.diesgut.domain.user.dto.CreateUserDto;
import com.diesgut.domain.user.dto.UpdateUserDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.CDI)
public interface UpdateUserEntityMapper {
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDto(UpdateUserDto dto, @MappingTarget UserEntity entity);
}
