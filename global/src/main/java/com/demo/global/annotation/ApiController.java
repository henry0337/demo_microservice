package com.demo.global.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Đơn giản hóa việc định nghĩa một <b>controller</b> cho REST API bằng cách kết hợp {@link RestController @RestController}
 * và {@link RequestMapping @RequestMapping}.
 * <p>
 * Annotation này được sử dụng trên một lớp để đánh dấu nó là một REST Controller và ánh xạ các yêu cầu tới một đường dẫn cụ thể.
 *
 * <p><b>Ví dụ:</b></p>
 * <pre>{@code
 * @ApiController("/api/test")
 * public class TestController { }
 * }</pre>
 *
 * @see RestController
 * @see RequestMapping
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@RestController
@RequestMapping
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiController {

    /**
     * Chỉ định một hoặc nhiều URI ánh xạ tới đường dẫn tài nguyên.
     * <p>
     * Hỗ trợ cú pháp kiểu <a href="https://ant.apache.org/manual/using.html#path">Ant</a>, ví dụ: {@code /test/**}.
     *
     * @return Mảng các đường dẫn URI, mặc định là {@code {}}.
     */
    @AliasFor(attribute = "path", annotation = RequestMapping.class)
    String[] value() default {};
}