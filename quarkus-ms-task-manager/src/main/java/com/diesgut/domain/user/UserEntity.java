package com.diesgut.domain.user;

import com.diesgut.common.PanacheBaseEntity;
import com.diesgut.domain.project.ProjectEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})
        }
)
public class UserEntity extends PanacheBaseEntity {

    @Column(unique = true, nullable = false)
    public String name;

    @Column(nullable = false)
    public String password;

    @ElementCollection(fetch = FetchType.EAGER) // EAGER to load roles immediately with the user
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    public List<String> roles;
}