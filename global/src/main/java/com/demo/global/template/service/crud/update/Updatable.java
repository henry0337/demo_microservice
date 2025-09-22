package com.demo.global.template.service.crud.update;

import com.demo.global.helper.Result;

/**
 * Giao diện hàm để cập nhật thực thể dựa trên tiêu chí.
 * @param <C> Kiểu của tiêu chí
 * @param <T> Kiểu của thực thể
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@FunctionalInterface
public interface Updatable<C, T> {
    /**
     * Cập nhật một thực thể theo tiêu chí và trả về kết quả hoặc ngoại lệ.
     * @param criteria Tiêu chí để xác định thực thể
     * @param body Dữ liệu thực thể đã cập nhật
     * @return Kết quả chứa kết quả cập nhật hoặc ngoại lệ
     */
    Result<?, Exception> update(C criteria, T body);
}
