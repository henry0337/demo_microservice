package com.demo.global.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;

/**
 * Đánh dấu một lớp {@link Configuration} để tùy chỉnh các tính năng cơ bản của
 * <a href="https://docs.spring.io/spring-security/reference/servlet/getting-started.html">Spring MVC framework</a>.
 * <p>
 * Annotation này kích hoạt cấu hình MVC thông qua {@link EnableWebMvc} và áp dụng trên các lớp cấu hình.
 *
 * @see Configuration
 * @see EnableWebMvc
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Configuration
@EnableWebMvc
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServletConfiguration {
    /**
     * Xác định xem các phương thức {@link Bean @Bean} trong lớp được đánh dấu có nên được
     * <a href="https://docs.spring.io/spring-framework/reference/core/aop/proxying.html">proxy bởi CGLIB</a>
     * để đảm bảo tuân thủ hành vi vòng đời của bean hay không.
     * <p>
     * Giá trị mặc định: {@code true}
     *
     * @return {@code true} nếu proxy được kích hoạt, {@code false} nếu không.
     */
    @AliasFor(attribute = "proxyBeanMethods", annotation = Configuration.class)
    boolean proxyMethods() default true;
}
