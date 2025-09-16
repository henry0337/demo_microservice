package com.demo.global.annotation;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//
//import java.lang.annotation.*;
//
// /**
// * Kích hoạt cấu hình <a href="https://kafka.apache.org/">Apache Kafka</a> cho ứng dụng Spring.
// * <p>
// * Khi được áp dụng cho một lớp cấu hình, chú thích này hỗ trợ cấu hình Kafka bằng cách đăng ký các bean và các cấu hình
// * cần thiết cho việc xử lý giao tiếp dữ liệu giữa các <a href="https://kafka.apache.org/documentation/#consumerapi">consumer</a>
// * và <a href="https://kafka.apache.org/documentation/#producerapi">producer</a> của Kafka thông qua {@link EnableKafka @EnableKafka}.
// * </p>
// *
// * @see EnableKafka
// * @see Configuration
// * @author <a href="https://github.com/henry0337">Moineau</a>
// */
//@Configuration
//@EnableKafka
//@Target(ElementType.TYPE)
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
public @interface ApacheKafkaConfiguration { }
