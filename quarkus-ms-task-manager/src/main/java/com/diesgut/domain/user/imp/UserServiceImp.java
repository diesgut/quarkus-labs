package com.diesgut.domain.user.imp;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.user.UserService;
import com.diesgut.domain.user.UserEntity;
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
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@ApplicationScoped
public class UserServiceImp implements UserService {
    private final UserEntityMapper userEntityMapper;
    private final CreateUserEntityMapper createUserEntityMapper;
    private final UpdateUserEntityMapper updateUserEntityMapper;


    @WithSession
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
    @WithSession
    public Uni<UserEntity> findByName(String name) {
        return UserEntity.find("name", name).firstResult(); //if the user is not found, it will return null
    }

    @Override
    @WithSession
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
        String password = BcryptUtil.bcryptHash(user.getPassword()); // Hash the password before saving
        user.setPassword(password);
        return userEntity.persistAndFlush().onItem()
                .transform(userEntitySaved -> {
                    return userEntityMapper.toDto((UserEntity) userEntitySaved);
                });
    }

    @Override
    @WithTransaction
    public Uni<UserDto> update(UpdateUserDto user) {
        return findEntityById(user.getId())
                // 1. Usa .chain() porque la operación interna devuelve un Uni
                .chain(userEntity -> {
                    updateUserEntityMapper.updateEntityFromDto(user, userEntity);
                    return userEntity.persistAndFlush();
                })
                // 2. Usa .transform() para la conversión final a DTO
                .onItem().transform(userEntityUpdated -> {
                    return userEntityMapper.toDto((UserEntity) userEntityUpdated);
                });
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
        // TODO: replace implementation once security is added to the project
        return UserEntity.find("order by ID").firstResult();
    }

    @WithSession
    @Override
    public Uni<UserDto> getCurrentUserDto() {
        return this.getCurrentUser()
                .onItem()
                .transform(userEntity -> userEntityMapper.toDto((UserEntity) userEntity));
    }
/*
    public Uni<UserEntity> authenticate(String name, String password) {
        return UserEntity.find("name = ?1 and password = ?2", name, password).firstResult();
    }*/


/*
    // Buscar usuarios por rol
    public static List<UserEntity> findByRole(String role) {
        return list("roles", role);
    }

    // Buscar usuarios por persona
    public static List<UserEntity> findByPerson(PersonEntity person) {
        return list("person", person);
    }**/

    public static boolean matches(UserEntity user, String password) {
        return BcryptUtil.matches(password, user.password);
    }
}
