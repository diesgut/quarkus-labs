package com.diesgut.domain.person;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface PersonService {
    Uni<PersonEntity> findById(Long id);
    Uni<PersonEntity> findByName(String name);
    Uni<List<PersonEntity>> list();
    Uni<PersonEntity> create(PersonEntity user);
    Uni<PersonEntity> update(PersonEntity user);
    Uni<Void> delete(Long id);
}
