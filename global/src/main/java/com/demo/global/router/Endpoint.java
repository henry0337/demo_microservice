package com.demo.global.router;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>Nơi định nghĩa các đường dẫn để điều hướng người dùng qua các trang khác nhau.</p>
 * <br>
 * Cú pháp bổ sung (khuyên dùng):
 * <ul>
 *     <li>{@code "/{{path-uri}}"}: Thay thế {@code {path-uri}} theo nhu cầu, hoạt động giống
 *     {@link org.springframework.web.bind.annotation.PathVariable @PathVariable}.</li>
 * </ul>
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Endpoint {
    private static final String BASE_API = "/api/v1";

    public static final String USER = BASE_API + "/user";

    public static final String AUTH = BASE_API + "/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String USER_INFO = "/userInfo";

    public static final String PAYMENT = "/payment";
}
