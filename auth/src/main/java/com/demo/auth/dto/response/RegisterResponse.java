package com.demo.auth.dto.response;

public record RegisterResponse(
        String name,
        String email,
        String avatar
) { }

