package com.demo.auth.service;

import com.demo.auth.client.UserClient;
import com.demo.global.helper.Result;
import com.demo.auth.dto.request.LoginRequest;
import com.demo.auth.dto.response.LoginResponse;
import com.demo.auth.dto.request.RegisterRequest;
import com.demo.auth.dto.response.RegisterResponse;
import com.demo.auth.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
    AuthenticationManager   manager;
    ConversionService       conversionService;
    JwtService              jwtService;
    UserClient              client;
    PasswordEncoder         encoder;

    public RegisterResponse register(@NonNull RegisterRequest request) {
        try {
            var response = conversionService.convert(request, RegisterResponse.class);
            User newUser = conversionService.convert(response, User.class);
            if (newUser == null)
                return new RegisterResponse("", "", "");
            newUser.setPassword(encoder.encode(request.getPassword()));
            client.save(newUser);
            return response;
        } catch (Exception e) {
            return new RegisterResponse("", "", "");
        }
    }

    public LoginResponse login(@NonNull LoginRequest body) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));

            var expectUser = client.findByEmail(body.getEmail());
            User user = expectUser instanceof Result.Success<User> currentUser && currentUser.getBody() != null ? currentUser.getBody() : null;
            if (user == null) return null;

            String jwt = jwtService.generateTokenWithUserInfo(user);
            String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
            return new LoginResponse(jwt, refreshToken);
        } catch (Exception ignored) {
            return new LoginResponse("", "");
        }
    }
}
