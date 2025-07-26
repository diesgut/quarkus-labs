package com.diesgut.domain.user.imp;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.user.UserEntity;
import com.diesgut.domain.user.UserService;
import com.diesgut.domain.user.dto.CreateUserDto;
import com.diesgut.domain.user.dto.UpdateUserDto;
import com.diesgut.domain.user.dto.UserDto;
import com.diesgut.domain.user.mapper.CreateUserEntityMapper;
import com.diesgut.domain.user.mapper.UpdateUserEntityMapper;
import com.diesgut.domain.user.mapper.UserEntityMapper;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@WithSession
@ApplicationScoped
public class UserServiceImp implements UserService {
    private final UserEntityMapper userEntityMapper;
    private final CreateUserEntityMapper createUserEntityMapper;
    private final UpdateUserEntityMapper updateUserEntityMapper;

    private final JsonWebToken jwt;

    @Override
    public Uni<UserDto> findById(Long id) {
        return findEntityById(id)
                .onItem()
                .transform(userEntityMapper::toDto);
    }

    private Uni<UserEntity> findEntityById(Long id) {
        return UserEntity.<UserEntity>findById(id)
                .onItem()
                .ifNull() // If the user is not found, return null
                .failWith(() -> new ObjectNotFoundException(id, "User"));
    }

    @Override
    public Uni<UserEntity> findByName(String name) {
        return UserEntity.find("name", name).firstResult(); //if the user is not found, it will return null
    }

    @Override
    public Uni<List<UserDto>> list() {
        return UserEntity.listAll() // If there are no users, it will return an empty list
                .onItem().transform(entityList ->
                        entityList.stream()
                                .map(userEntity -> userEntityMapper.toDto((UserEntity) userEntity))
                                .toList()
                );
    }

    @Override
    @WithTransaction // This annotation ensures that the method runs within a reactive transaction
    public Uni<UserDto> create(CreateUserDto user) {
        UserEntity userEntity = createUserEntityMapper.toEntity(user);
        // Hash the password before saving
        userEntity.password = BcryptUtil.bcryptHash(user.getPassword());
        return userEntity.<UserEntity>persistAndFlush().onItem()
                .transform(userEntityMapper::toDto);
    }

    @Override
    @WithTransaction
    public Uni<UserDto> update(UpdateUserDto user) {
        return findEntityById(user.getId())
                // 1. Usa .chain() porque la operación interna devuelve un Uni
                .chain(userEntity -> {
                    if (userEntity.version != user.getVersion()) {
                        // Lanza un Uni fallido con la excepción de concurrencia
                        return Uni.createFrom().failure(new OptimisticLockException(
                                "User " + user.getId() + " has been modified by another transaction"
                        ));
                    }
                    updateUserEntityMapper.updateEntityFromDto(user, userEntity);
                    return userEntity.<UserEntity>persistAndFlush();
                })
                // 2. Usa .transform() para la conversión final a DTO
                .onItem().transform(userEntityMapper::toDto);
    }

    @Override
    @WithTransaction
    public Uni<Void> delete(Long id) {
        return findEntityById(id)
                .chain(user -> Uni.combine().all().unis(
                                        TaskEntity.delete("userEntity.id", user.id),
                                        ProjectEntity.delete("userEntity.id", user.id)
                                ).asTuple()
                                .chain(t -> user.delete())
                );
    }

    @Override
    public Uni<UserEntity> getCurrentUser() {
        String principalName = jwt.getName(); //Needs the bearer token to be set in the request header
        return this.findByName(principalName);
    }

    @WithSession
    @Override
    public Uni<UserDto> getCurrentUserDto() {
        return this.getCurrentUser()
                .onItem()
                .transform(userEntityMapper::toDto);
    }

    @Override
    public Uni<UserDto> changePassword(String currentPassword, String newPassword) {
        return getCurrentUser()
                .chain(user -> {
                    if (!BcryptUtil.matches(currentPassword, user.password)) {
                        throw new ClientErrorException("Current password does not match", Response.Status.CONFLICT);
                    }
                    user.password = BcryptUtil.bcryptHash(newPassword);
                    return user.<UserEntity>persistAndFlush();
                }).map(userEntityMapper::toDto);
    }
}
