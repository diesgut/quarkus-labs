package com.diesgut.domain.task.dto;

import lombok.Data;

@Data
public class CreateTaskDto {
    private String title;
    private String description;
    private Integer priority;
    public Long projectId;
}
