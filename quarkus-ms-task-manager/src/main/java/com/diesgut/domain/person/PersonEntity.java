package com.diesgut.domain.person;

import com.diesgut.common.PanacheBaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@Entity
@Table(
        name = "persons",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"document_type", "document_number"})
        }
)
public class PersonEntity extends PanacheBaseEntity {
    @Column(unique = true, nullable = false)
    public String first_name;

    @Column(unique = true, nullable = false)
    public String last_name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public PersonConstants.DocumentType document_type;

    @Column(unique = true, nullable = false)
    public String document_number;
}
