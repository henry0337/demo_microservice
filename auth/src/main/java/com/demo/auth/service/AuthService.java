package com.demo.auth.service;

import com.demo.auth.client.UserClient;
import com.demo.auth.dto.request.ChangePasswordRequest;
import com.demo.auth.dto.response.ChangePasswordResponse;
import com.demo.auth.mapper.UserMapper;
import com.demo.global.helper.Result;
import com.demo.auth.dto.request.LoginRequest;
import com.demo.auth.dto.response.LoginResponse;
import com.demo.auth.dto.request.RegisterRequest;
import com.demo.auth.dto.response.RegisterResponse;
import com.demo.auth.model.User;
import com.demo.global.service.MailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
    JwtService              jwtService;
    MailService             mailService;
    AuthenticationManager   manager;
    UserClient              client;
    PasswordEncoder         encoder;
    UserMapper              mapper;

    public Result<RegisterResponse, Exception> register(@NonNull RegisterRequest request) {
        try {
            RegisterResponse response = mapper.fromRegisterRequestToItsResponse(request);
            User newUser = mapper.fromRegisterResponseToUser(response);
            newUser.setPassword(encoder.encode(request.getPassword()));
            client.save(newUser);
            return new Result.Success<>(201, response);
        } catch (Exception e) {
            return new Result.Failure<>(400, e);
        }
    }

    public LoginResponse login(@NonNull LoginRequest body) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));

        User expectUser = client.findByEmail(body.getEmail());
        if (expectUser == null) return null;

        String jwt = jwtService.generateTokenWithUserInfo(expectUser);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), expectUser);
        return new LoginResponse(jwt, refreshToken);
    }

    public ChangePasswordResponse resetPassword(ChangePasswordRequest request) {
        return null;
    }
}
