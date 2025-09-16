package com.demo.global.helper;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

/**
 * Lớp niêm phong biểu thị <b>kết quả của một tác vụ</b> với hai trạng thái:
 * <ul>
 *     <li>{@link Success Success}: Tác vụ <b>thành công</b>.</li>
 *     <li>{@link Failure Failure}: Tác vụ <b>thất bại</b>.</li>
 * </ul>
 *
 * @param <B> Kiểu dữ liệu trả về khi tác vụ thành công.
 * @param <E> Kiểu ngoại lệ trả về khi tác vụ thất bại.
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public sealed class Result<B, E extends Throwable> permits Result.Success, Result.Failure {
    /**
     * Mã trạng thái HTTP trả về.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status">Mã trạng thái HTTP</a>
     */
    @Getter
    int code;

    /**
     * Lớp đại diện cho trạng thái <b>thành công</b> của tác vụ.
     */
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static final class Success<B> extends Result<B, Exception> {
        @Getter
        B body;

        public Success(int code, B body) {
            super(code);
            this.body = body;
        }
    }

    /**
     * Lớp đại diện cho trạng thái <b>thất bại</b> của tác vụ.
     */
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static final class Failure<T, F extends Throwable> extends Result<T, F> {
        @Getter
        String message;

        public Failure(int code, @NotNull F error) {
            super(code);
            this.message = error.getLocalizedMessage();
        }
    }
}