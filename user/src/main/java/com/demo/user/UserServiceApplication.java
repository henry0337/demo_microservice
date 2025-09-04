package com.demo.user;

import com.demo.auth.config.SecurityConfiguration;
import com.demo.global.config.SwaggerConfiguration;
import com.demo.global.config.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@Import({WebConfiguration.class, SecurityConfiguration.class, SwaggerConfiguration.class})
@SpringBootApplication
@EnableCaching
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
