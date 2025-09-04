package com.demo.auth.controller;

import com.demo.auth.dto.request.LoginRequest;
import com.demo.auth.dto.response.LoginResponse;
import com.demo.auth.dto.request.RegisterRequest;
import com.demo.auth.dto.response.RegisterResponse;
import com.demo.auth.service.AuthService;
import com.demo.auth.service.JwtService;
import com.demo.auth.model.User;
import com.demo.global.annotation.ApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Moineau
 */
@ApiController("/api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    AuthService authService;
    JwtService jwtService;

    /**
     * Đăng ký người dùng mới và lưu thông tin vào cơ sở dữ liệu.
     *
     * @param request Thông tin đăng ký của người dùng mới
     * @return {@link ResponseEntity} chứa {@link RegisterResponse} với kết quả đăng ký.
     *
     * @throws IllegalArgumentException nếu dữ liệu yêu cầu không hợp lệ
     * @throws org.springframework.dao.DataIntegrityViolationException nếu tên đăng nhập hoặc email đã tồn tại
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(authService.register(request));
    }

    /**
     * Xác thực người dùng với thông tin đăng nhập được cung cấp và tạo các token xác thực.
     *
     * @param request Thông tin đăng nhập của người dùng
     * @return {@link ResponseEntity} chứa {@link LoginResponse} với access token và refresh token.
     *
     * @throws org.springframework.security.authentication.BadCredentialsException nếu thông tin đăng nhập không hợp lệ
     * @throws IllegalArgumentException nếu dữ liệu yêu cầu không hợp lệ
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Giải mã token JWT để lấy thông tin xác thực của người dùng.
     *
     * @param token JWT token cần giải mã
     * @return Thông tin xác thực của người dùng nếu giải mã thành công, ngược lại trả về {@code null}.
     *
     * @throws io.jsonwebtoken.ExpiredJwtException         nếu token đã hết hạn
     * @throws io.jsonwebtoken.MalformedJwtException       nếu token không đúng định dạng
     * @throws io.jsonwebtoken.security.SignatureException nếu chữ ký token không hợp lệ
     * @throws IllegalArgumentException                    nếu token là {@code null} hoặc rỗng
     *
     * @apiNote Để kiểm thử đáng tin cậy hơn, nên sử dụng <b>Postman</b> thay vì <b>Swagger</b>.<br>
     * Nếu vẫn muốn thử với <b>Swagger</b>, hãy để trống tham số {@code token}
     * và thay vào đó sử dụng trường <b>Bearer Token</b> trong phần xác thực — cách này sẽ hoạt động đúng như mong đợi.
     */
    @GetMapping("/userInfo")
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
    public ResponseEntity<User> obtainUserInfoUsingJwtToken(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        String obtainedToken = null;

        if (token == null) {
            obtainedToken = extractJwtTokenFromSecurityContext();
        } else if (token.startsWith("Bearer ")) {
            obtainedToken = token.substring(7);
        }

        return ResponseEntity.ofNullable(jwtService.obtainUserFromToken(obtainedToken));
    }

    private String extractJwtTokenFromSecurityContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        } else {
            log.warn("SecurityContext did not find any token. Did you forget using \"Bearer Token\" field ?");
            throw new IllegalStateException("SecurityContext doesn't contain any token");
        }
    }
}
