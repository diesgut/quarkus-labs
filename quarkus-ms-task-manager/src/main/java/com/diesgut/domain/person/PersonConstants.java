package com.diesgut.domain.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PersonConstants {
    @Getter
    @RequiredArgsConstructor
    public enum DocumentType {
        DNI,
        PASSPORT;
    }
}
