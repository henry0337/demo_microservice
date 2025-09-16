package com.demo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Dịch vụ xử lý thông tin người dùng.</p>
 * <b>Tên client trong Eureka Discovery</b>: {@code user-service}
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@SpringBootApplication
@ComponentScan({"com.demo.global", "com.demo.auth", "com.demo.user"})
@EnableCaching(proxyTargetClass = true)
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
