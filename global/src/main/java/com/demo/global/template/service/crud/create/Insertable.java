package com.demo.global.template.service.crud.create;

import com.demo.global.helper.Result;

/**
 * Giao diện hàm để lưu một thực thể.
 * @param <T> Kiểu của thực thể
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@FunctionalInterface
public interface Insertable<T> {
    /**
     * Lưu một thực thể và trả về kết quả hoặc ngoại lệ.
     * @param <C> Ám chỉ "<b>child</b>" - có thể là {@link T} hoặc là kiểu con của {@link T}
     * @param body Thực thể cần lưu
     * @return Kết quả chứa thực thể đã lưu hoặc ngoại lệ
     */
    <C extends T> Result<?, Exception> save(C body);
}
