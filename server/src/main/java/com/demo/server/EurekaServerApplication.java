package com.demo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p>Một máy chủ nội bộ Eureka giúp xử lý các dịch vụ con bên trong microservice.</p>
 * URL mặc định tới <b>Eureka Dashboard</b>: <pre><a href="http://localhost:8761/">http://localhost:8761/</a></pre>
 *
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

// Chuẩn bị cho JDK 25 (dựa theo mục số 512 thuộc quy trình đề xuất cải tiến Java)
//@SpringBootApplication
//@EnableEurekaServer
//class EurekaServerApplication {
//    void main(String[] args) {
//        SpringApplication.run(EurekaServerApplication.class, args);
//    }
//}