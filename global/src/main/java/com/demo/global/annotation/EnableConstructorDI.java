package com.demo.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Chỉ định lớp sử dụng annotation này sẽ áp dụng <a href="https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html#beans-constructor-injection">constructor injection</a>
 * để tiêm phụ thuộc, thay vì sử dụng các cách khác như khởi tạo trực tiếp, field injection hoặc setter-based injection.
 * <p>
 * Annotation này tác động đến mã do trình biên dịch tạo ra như sau:
 * <ul>
 *     <li>Đánh dấu các trường trong lớp là {@code private} và {@code final}.</li>
 *     <li>Tạo hàm khởi tạo cho các trường được đánh dấu {@code private final}.</li>
 * </ul>
 * <p>
 * <b>Lưu ý:</b> Vì các trường được đánh dấu {@code final}, để bỏ thuộc tính này, sử dụng annotation {@link lombok.experimental.NonFinal @NonFinal}
 * của <a href="https://projectlombok.org/">Lombok</a> trên các trường cần loại bỏ {@code final}.
 *
 * @see lombok.experimental.NonFinal
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Deprecated
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EnableConstructorDI { }
