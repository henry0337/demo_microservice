package com.demo.auth.client;

import com.demo.auth.model.User;
import com.demo.global.config.FeignConfiguration;
import com.demo.global.helper.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.demo.global.router.Endpoint.USER;

/**
 * <p><b>Feign Client</b> giúp tương tác với <b>User Service</b>.</p>
 *
 * Nếu như <a href="https://docs.spring.io/spring-cloud-circuitbreaker/reference/3.2/index.html">Spring Cloud Circuit Breaker</a>
 * xuất hiện trong classpath và bạn KHÔNG muốn sử dụng nó trong OpenFeign thì hãy cung cấp một
 * {@link feign.Feign.Builder Feign.Builder} mặc định với scope là {@code prototype} như dưới:
 * <pre>{@code
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Scope;
 * import feign.Feign.Builder;
 *
 * @Configuration
 * public class FeignConfiguration {
 *    @Bean
 *    @Scope("prototype")
 *    public Feign.Builder feignBuilder() {
 * 	   return Feign.builder();
 *    }
 * }
 * }</pre>
 *
 * @author <a href="https://github.com/henry0337">Moineau</a>
 * @apiNote <b>OpenFeign</b> client <b>chỉ</b> có thể hoạt động trên môi trường <b>đồng bộ (blocking-only)</b>.
 * @see <a href="https://docs.spring.io/spring-cloud-openfeign/reference/spring-cloud-openfeign.html">Spring Cloud OpenFeign</a>
 * @see FeignClient
 */
@FeignClient(name = "user-service", configuration = FeignConfiguration.class, fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping(USER + "/{email}")
    User findByEmail(@PathVariable String email);

    @PostMapping(USER)
    @SuppressWarnings("UnusedReturnValue")
    Result<?, Exception> save(@RequestBody User user);
}

/**
 * <p>Lớp fallback nếu như {@link UserClient} trả về một trong các mã phản hồi thất bại <b>(400..500)</b>.</p>
 *
 * @author <a href="https://github.com/henry0337">Moineau</a>
 * @implNote Nếu như Spring Cloud Circuit Breaker xuất hiện trong module mà client đang ở đó và thuộc tính
 * {@code spring.cloud.openfeign.circuitbreaker.enabled=true} (mặc định) thì lớp này bắt buộc phải được triển khai và cung
 * cấp cho client tương ứng.
 * @see <a href="https://docs.spring.io/spring-cloud-openfeign/reference/spring-cloud-openfeign.html#spring-cloud-feign-circuitbreaker">Circuit Breaker support for OpenFeign</a>
 * @see <a href="https://docs.spring.io/spring-cloud-openfeign/reference/spring-cloud-openfeign.html#spring-cloud-feign-circuitbreaker-fallback">Feign Client with Circuit Breaker fallbacks</a>
 */
@Component
class UserClientFallback implements UserClient {

    @Override
    public User findByEmail(String email) {
        return User.builder().build();
    }

    @Override
    public Result<?, Exception> save(User user) {
        return new Result.Failure<>(400, new RuntimeException());
    }
}
