package com.demo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Dịch vụ xử lý xác thực và ủy quyền người dùng.</p>
 * <b>Tên client trong Eureka Discovery</b>: {@code auth-service}
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@SpringBootApplication
@ComponentScan({"com.demo.global", "com.demo.auth"})
@EnableCaching(proxyTargetClass = true)
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
