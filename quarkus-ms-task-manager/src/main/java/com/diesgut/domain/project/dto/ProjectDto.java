package com.diesgut.domain.project.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ProjectDto {
    public Long id;
    public String name;
    public Long userId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
