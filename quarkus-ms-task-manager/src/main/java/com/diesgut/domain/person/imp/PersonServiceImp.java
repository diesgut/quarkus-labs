package com.diesgut.domain.person.imp;

import com.diesgut.domain.person.PersonEntity;
import com.diesgut.domain.person.PersonService;
import com.diesgut.domain.user.UserService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@ApplicationScoped
public class PersonServiceImp implements PersonService {
    private final UserService userService;


    @Override
    public Uni<PersonEntity> findById(Long id) {
        return userService.getCurrentUser()
                .chain(user -> PersonEntity.<PersonEntity>findById(id)
                        .onItem().ifNull().failWith(() -> new
                                ObjectNotFoundException(id, "Person not found")));

    }

    @Override
    public Uni<PersonEntity> findByName(String name) {
        return null;
    }

    @Override
    public Uni<List<PersonEntity>> list() {
        return null;
    }

    @Override
    public Uni<PersonEntity> create(PersonEntity user) {
        return null;
    }

    @Override
    public Uni<PersonEntity> update(PersonEntity user) {
        return null;
    }

    @Override
    public Uni<Void> delete(Long id) {
        return null;
    }
}
