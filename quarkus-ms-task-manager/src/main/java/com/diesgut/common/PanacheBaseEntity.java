package com.diesgut.common;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Setter
@Getter
@MappedSuperclass
public abstract class PanacheBaseEntity extends PanacheEntityBase {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private ZonedDateTime created_at;

    //@UpdateTimestamp
    private ZonedDateTime updated_at;


    @Version
    private int version;

    public PanacheBaseEntity() {
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_at = ZonedDateTime.now(ZoneOffset.UTC);
    }

    public String toString() {
        String var10000 = this.getClass().getSimpleName();
        return var10000 + "<" + this.id + ">";
    }
}