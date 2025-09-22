package com.demo.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * <p>Dịch vụ xử lý các giao dịch thanh toán.</p>
 * <b>Tên client trong Eureka Discovery</b>: {@code payment-service}
 *
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}