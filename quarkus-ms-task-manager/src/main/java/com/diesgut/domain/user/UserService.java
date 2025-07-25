package com.diesgut.domain.user;

import com.diesgut.domain.user.dto.CreateUserDto;
import com.diesgut.domain.user.dto.UpdateUserDto;
import com.diesgut.domain.user.dto.UserDto;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface UserService {
    Uni<UserDto> findById(Long id);
    Uni<UserEntity> findByName(String name);
    Uni<List<UserDto>> list();
    Uni<UserDto> create(CreateUserDto user);
    Uni<UserDto> update(UpdateUserDto user);
    Uni<Void> delete(Long id);
    Uni<UserEntity> getCurrentUser();
    Uni<UserDto> getCurrentUserDto();
}
