package com.diesgut.domain.user.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class CreateUserDto {
    private Long id;
    private String name;
    private String password;
    private List<String> roles;
}
