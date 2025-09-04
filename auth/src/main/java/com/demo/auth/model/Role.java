package com.demo.auth.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public enum Role {
    ADMIN("Quản trị viên"),
    USER("Người dùng phổ thông");

    @Getter
    String displayName;
}
