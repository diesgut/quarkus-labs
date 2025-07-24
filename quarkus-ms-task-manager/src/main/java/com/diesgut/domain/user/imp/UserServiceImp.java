package com.diesgut.domain.user.imp;

import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.task.TaskEntity;
import com.diesgut.domain.user.UserService;
import com.diesgut.domain.user.UserEntity;
import com.diesgut.domain.user.dto.UserDto;
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

    public Uni<UserEntity> findById(Long id) {
        return UserEntity.<UserEntity>findById(id)
                .onItem()
                .ifNull() // If the user is not found, return null
                .failWith(() -> new ObjectNotFoundException(id, "User not found"));
    }

    public Uni<UserEntity> findByName(String name) {
        return UserEntity.find("name", name).firstResult(); //if the user is not found, it will return null
    }

    @WithSession
    public Uni<List<UserDto>> list() {
        return UserEntity.listAll() // If there are no users, it will return an empty list
                .onItem().transform(entityList ->
                        entityList.stream()
                                .map(userEntity -> userEntityMapper.toDto((UserEntity) userEntity))
                                .toList()
                );
    }

    @WithTransaction // This annotation ensures that the method runs within a reactive transaction
    public Uni<UserEntity> create(UserEntity user) {
        String password = BcryptUtil.bcryptHash(user.getPassword()); // Hash the password before saving
        user.setPassword(password);
        return user.persistAndFlush();
    }

    @WithTransaction
    public Uni<UserEntity> update(UserEntity user) {
        return findById(user.getId())
                .chain(u -> UserEntity.getSession()) // Get the current session, chaining the previous Uni
                .chain(s -> s.merge(user)); // Merge the user entity with the current session, evaluate version and update it
    }

    @WithTransaction
    public Uni<Void> delete(Long id) {
        return findById(id)
                .chain(user -> Uni.combine().all().unis(
                                        TaskEntity.delete("userEntity.id", user.getId()),
                                        ProjectEntity.delete("userEntity.id", user.getId())
                                ).asTuple()
                                .chain(t -> user.delete())
                );
    }

    public Uni<UserEntity> getCurrentUser() {
        // TODO: replace implementation once security is added to the project
        return UserEntity.find("order by ID").firstResult(); // if the user is not found, it will return null
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
}
