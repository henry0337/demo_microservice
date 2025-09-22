package com.demo.global.template.service.crud.read;

import com.demo.global.helper.Result;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Giao diện để đọc các thực thể.
 * @param <T> Kiểu của thực thể
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@FunctionalInterface
public interface Readable<T> {
    /**
     * Lấy tất cả các thực thể.
     * @return Kết quả chứa tất cả thực thể hoặc ngoại lệ
     */
    Result<List<T>, Exception> findAll();

    /**
     * Lấy tất cả thực thể với sắp xếp (mặc định trả về null).
     * @param sort Tiêu chí sắp xếp
     * @return Kết quả chứa các thực thể đã sắp xếp hoặc ngoại lệ
     */
    default Result<List<T>, Exception> findAll(@NonNull Sort sort) {
        return null;
    }
}
