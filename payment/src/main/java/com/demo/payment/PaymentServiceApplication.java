package com.demo.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Dịch vụ xử lý các giao dịch thanh toán.</p>
 * <b>Tên client trong Eureka Discovery</b>: {@code payment-service}
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@SpringBootApplication
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
