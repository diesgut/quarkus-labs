package com.diesgut.common;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class PanacheBaseEntity extends PanacheEntityBase {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    public Long id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    public ZonedDateTime created_at;

    //@UpdateTimestamp
    public ZonedDateTime updated_at;


    @Version
    public int version;

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