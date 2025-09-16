package com.demo.global.template.service.crud.read;

import com.demo.global.helper.Result;

/**
 * Giao diện để đọc thực thể theo ID, mở rộng từ Readable.
 * @param <T> Kiểu của thực thể
 * @param <ID> Kiểu của ID
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
public interface ReadableWithID<T, ID> extends Readable<T> {
    /**
     * Lấy một thực thể theo ID.
     * @param id ID của thực thể
     * @return Kết quả chứa thực thể hoặc ngoại lệ
     */
    Result<T, Exception> findById(ID id);
}
