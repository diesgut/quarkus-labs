package com.diesgut.domain.project;

import com.diesgut.common.PanacheBaseEntity;
import com.diesgut.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "projects",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "user_id"})
        }
)
public class ProjectEntity extends PanacheBaseEntity {
    @Column(nullable = false)
    public String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    public UserEntity userEntity;
}
