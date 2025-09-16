package com.demo.global.template.service.crud.read;

import com.demo.global.helper.Result;

/**
 * Giao diện để đọc thực thể dựa trên tiêu chí, mở rộng từ giao diện {@link Readable}.
 * @param <T> Kiểu của thực thể
 * @param <C> Kiểu của tiêu chí
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
public interface ReadableWithCriteria<T, C> extends Readable<T> {
    /**
     * Lấy thực thể theo tiêu chí.
     * @param criteria Tiêu chí để lọc thực thể
     * @return Kết quả chứa các thực thể khớp hoặc ngoại lệ
     */
    Result<T, Exception> findByCriteria(C criteria);
}
