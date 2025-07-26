package com.diesgut.domain.user.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private int version;
    private List<String> roles;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
