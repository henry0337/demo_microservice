package com.demo.global.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.TimeoutRetryPolicy;

import java.time.Duration;

/**
 * Lớp cấu hình <b>Circuit Breaker</b>.
 * @see <a href="https://docs.spring.io/spring-cloud-circuitbreaker/reference/3.2/index.html">Spring Cloud Circuit Breaker</a>
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@Configuration
public class CircuitBreakerConfiguration {
    /**
     * Bean cấu hình thông số mặc định cho <b>Resilience4J</b>.
     * @see <a href="https://docs.spring.io/spring-cloud-circuitbreaker/reference/3.2/spring-cloud-circuitbreaker-resilience4j.html">Resilience4J Circuit Breakers</a>
     */
    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .build());
    }

    /**
     * Bean cấu hình các thông số mặc định cho <b>Spring Retry</b>.
     * @see <a href="https://docs.spring.io/spring-cloud-circuitbreaker/reference/3.2/spring-cloud-circuitbreaker-spring-retry.html">Spring Retry Circuit Breakers</a>
     */
    @Bean
    Customizer<SpringRetryCircuitBreakerFactory> defaultRetryCustomizer() {
        return factory -> factory.configureDefault(id -> new SpringRetryConfigBuilder(id)
                .retryPolicy(new TimeoutRetryPolicy()).build());
    }
}
