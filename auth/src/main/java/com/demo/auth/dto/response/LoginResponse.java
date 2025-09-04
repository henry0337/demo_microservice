package com.demo.auth.dto.response;

public record LoginResponse(
        String token,
        String refreshToken
) { }
