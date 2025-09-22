package com.demo.global.template.service.crud.delete;

import com.demo.global.helper.Result;

/**
 * Giao diện hàm để xóa một thực thể dựa trên tiêu chí.
 * @param <C> Kiểu của tiêu chí
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@FunctionalInterface
public interface Deletable<C> {
    /**
     * Xóa một thực thể theo tiêu chí và trả về kết quả hoặc ngoại lệ.
     * @param criteria Tiêu chí để xác định thực thể
     * @return Kết quả chứa kết quả xóa hoặc ngoại lệ
     */
    Result<?, Exception> delete(C criteria);
}
