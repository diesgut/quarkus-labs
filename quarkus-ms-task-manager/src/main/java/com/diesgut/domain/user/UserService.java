package com.diesgut.domain.user;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface UserService {
    Uni<UserEntity> findById(Long id);
    Uni<UserEntity> findByName(String name);
    Uni<List<UserEntity>> list();
    Uni<UserEntity> create(UserEntity user);
    Uni<UserEntity> update(UserEntity user);
    Uni<Void> delete(Long id);
    Uni<UserEntity> getCurrentUser();
}
