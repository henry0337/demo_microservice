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
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    JwtService              jwtService;
    MailService             mailService;
    AuthenticationManager   manager;
    UserClient              client;
    PasswordEncoder         encoder;
    UserMapper              mapper;

    public Result<RegisterResponse, Exception> register(@NonNull RegisterRequest request) {
        RegisterResponse response = mapper.fromRegisterRequestToItsResponse(request);
        User newUser = mapper.fromRegisterResponseToUser(response);
        try {
            newUser.setPassword(response.password());
            client.save(newUser);
            return new Result.Success<>(201, response);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new Result.Failure<>(400, e);
        }
    }

    public Result<LoginResponse, Exception> login(@NonNull LoginRequest body) throws DisabledException, LockedException {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException("Thông tin dùng để kiểm tra xác thực không hợp lệ!");
        }

        var expectUser = client.findByEmail(body.getEmail());
        if (expectUser == null) return null;

        String jwt = jwtService.generateTokenWithUserInfo(expectUser);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), expectUser);
        return new Result.Success<>(201, new LoginResponse(jwt, refreshToken));
    }

    public Result<ChangePasswordResponse, Exception> resetPassword(ChangePasswordRequest request) {
        return null;
    }
}
