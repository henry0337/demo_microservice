package com.demo.auth.config;

import com.demo.auth.client.UserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Lớp cấu hình các bean thuộc về {@link RestClient}.
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@Configuration
public class HttpClientConfig {
    @Bean
    UserClient userClient() {
        var restClient = RestClient.builder()
                .baseUrl("http://localhost:8081/api/v1/user")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build();
        var adapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserClient.class);
    }
}