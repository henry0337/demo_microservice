package com.demo.global.config;

//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.timelimiter.TimeLimiterConfig;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
//import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.springretry.SpringRetryConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.retry.policy.TimeoutRetryPolicy;

/**
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
//@Configuration
public class CircuitBreakerConfiguration {
//    @Bean
//    Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
//        return factory ->
//                factory.configureDefault(id ->
//                        new Resilience4JConfigBuilder(id)
//                                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
//                                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//                                .build());
//    }
//
//    @Bean
//    Customizer<SpringRetryCircuitBreakerFactory> defaultRetryCustomizer() {
//        return factory -> factory
//                .configureDefault(id -> new SpringRetryConfigBuilder(id)
//                .retryPolicy(new TimeoutRetryPolicy()).build());
//    }
}
