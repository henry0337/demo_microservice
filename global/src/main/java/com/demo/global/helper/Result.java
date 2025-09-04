package com.demo.global.helper;

import lombok.*;
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
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public sealed class Result<B, E extends Throwable> permits Result.Success, Result.Failure {
    /**
     * Mã trạng thái HTTP trả về.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status">Mã trạng thái HTTP</a>
     */
    @Getter
    private final int code;

    /**
     * Lớp đại diện cho trạng thái <b>thành công</b> của tác vụ.
     */
    public static final class Success<B> extends Result<B, Exception> {
        @Getter
        private final B body;

        public Success(int code, B body) {
            super(code);
            this.body = body;
        }
    }

    /**
     * Lớp đại diện cho trạng thái <b>thất bại</b> của tác vụ.
     */
    public static final class Failure<E extends Throwable> extends Result<Object, E> {
        @Getter
        private final String message;

        public Failure(int code, @NotNull E error) {
            super(code);
            this.message = error.getLocalizedMessage();
        }
    }
}