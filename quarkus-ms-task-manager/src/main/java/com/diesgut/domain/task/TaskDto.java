package com.diesgut.domain.task;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TaskDto {
    public Long id;
    public String title;
    public String description;
    private Integer priority;
    public Long userId;
    public Long projectId;
    public Boolean completed;
    private ZonedDateTime completeAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
