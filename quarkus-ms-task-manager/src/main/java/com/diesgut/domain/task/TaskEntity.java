package com.diesgut.domain.task;

import com.diesgut.common.PanacheBaseEntity;
import com.diesgut.domain.project.ProjectEntity;
import com.diesgut.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "tasks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"title"}),
                @UniqueConstraint(columnNames = {"description"})
        }
)
public class TaskEntity extends PanacheBaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    public Integer priority;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private ZonedDateTime complete;

    @ManyToOne
    @JoinColumn(name = "project_id")
    public ProjectEntity projectEntity;
}
