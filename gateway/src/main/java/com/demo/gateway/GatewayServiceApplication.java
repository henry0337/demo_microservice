package com.demo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Dịch vụ đóng vai trò như cổng API giúp <b>điều hướng</b> tới các <b>dịch vụ</b> khác trong microservice.</p>
 * <b>Tên client trong Eureka Discovery</b>: {@code gateway-service}
 *
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@SpringBootApplication
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
