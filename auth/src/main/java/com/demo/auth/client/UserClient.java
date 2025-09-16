package com.demo.auth.client;

import com.demo.global.helper.Result;
import com.demo.auth.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * <b>REST Client</b> tương tác với <b>User Service</b>.
 * @see <a href="https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-http-interface">HTTP Interface</a>
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@HttpExchange("http://localhost:8081/api/v1/user")
public interface UserClient {
    @GetExchange("/{email}")
    User findByEmail(@PathVariable String email);

    @PostExchange
    Result<?, Exception> save(@RequestBody User user);
}
