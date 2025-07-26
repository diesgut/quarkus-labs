package com.diesgut.domain.task.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TaskDto {
    public Long id;
    private String title;
    private String description;
    private Integer priority;
    public Long userId;
    public Long projectId;
    private ZonedDateTime completeAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
