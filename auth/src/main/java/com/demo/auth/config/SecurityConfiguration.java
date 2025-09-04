package com.demo.auth.config;

import com.demo.auth.client.UserClient;
import com.demo.auth.model.User;
import com.demo.auth.service.AuthService;
import com.demo.auth.annotation.ServletSecurityConfiguration;
import com.demo.auth.model.Role;
import com.demo.global.helper.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@ServletSecurityConfiguration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    String[] globalAccessRoutes = {
            "/api/v1/auth/**",
            "/api/v1/user/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/user/**",
            "/api/shutdown"
    };

    String[] roleAuthorizingRoutes = {
            "/api/v1/auth/userInfo"
    };

    String[] adminOnlyRoutes = {};

    JwtFilter filter;
    UserClient client;

    @Bean
    SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(route -> route
                        .requestMatchers(globalAccessRoutes).permitAll()
                        .requestMatchers(roleAuthorizingRoutes).hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers(adminOnlyRoutes).hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        final var authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
        authenticationProvider.setPasswordEncoder(encoder());
        return authenticationProvider;
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(@NonNull AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    private UserDetailsService userDetailsService() {
        return username -> {
            Result<User, Exception> expectUser = client.findByEmail(username);
            return expectUser instanceof Result.Success<User> currentUser && currentUser.getBody() != null ? currentUser.getBody() : null;
        };
    }
}
