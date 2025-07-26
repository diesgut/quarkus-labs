package com.diesgut.domain.user.dto;

public record PasswordChangeDto(String currentPassword, String newPassword) {
}
