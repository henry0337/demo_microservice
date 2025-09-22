package com.demo.auth.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.*;

/**
 * Đánh dấu một lớp {@link Configuration @Configuration} được cấu hình dành riêng cho tính năng
 * <a href="https://spring.io/projects/spring-security#overview">bảo mật</a> thuộc
 * <a href="https://docs.spring.io/spring-security/reference/servlet/getting-started.html">framework WebMVC</a>.
 * @see EnableWebSecurity
 * @see EnableMethodSecurity
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(proxyTargetClass = true)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServletSecurityConfiguration {
    /**
     * Chỉ định liệu các phương thức {@link org.springframework.context.annotation.Bean @Bean} thuộc lớp đang được đánh
     * dấu bằng chú thích này có nên được <a href="https://docs.spring.io/spring-framework/reference/core/aop/proxying.html">proxy bởi CGLIB</a>
     * để ép tuân theo hành vi xử lý trong vòng đời của bean không.
     *
     * @return {@code true} là giá trị mặc định.
     */
    @AliasFor(attribute = "proxyBeanMethods", annotation = Configuration.class)
    boolean proxyMethods() default true;
}
