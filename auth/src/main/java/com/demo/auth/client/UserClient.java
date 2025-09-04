package com.demo.auth.client;

import com.demo.global.helper.Result;
import com.demo.auth.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Moineau
 */
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users")
    Result<User, Exception> findByEmail(@RequestParam String email);

    @PostMapping
    Result<?, Exception> save(@RequestBody User user);
}
