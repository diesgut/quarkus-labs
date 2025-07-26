package com.diesgut.domain.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserDto {
    private Long id;
    private String name;
    private int version;
    private List<String> roles;
}
