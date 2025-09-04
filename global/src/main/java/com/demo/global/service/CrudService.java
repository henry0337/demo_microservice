package com.demo.global.service;

import com.demo.global.helper.Result;
import org.jetbrains.annotations.NotNull;

/**
 * Giao diện định nghĩa các phương thức CRUD tiêu chuẩn, được tùy chỉnh để phù hợp với
 * <a href="https://docs.spring.io/spring-data/jpa/reference/jpa.html">Spring Data JPA</a>
 * trong các lớp đánh dấu bởi {@link org.springframework.stereotype.Service @Service}.
 *
 * @param <T> Kiểu thực thể được truy vấn.
 * @param <ID> Kiểu dữ liệu của khóa chính.
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 * @apiNote Để truy vấn bằng các tiêu chí khác (như {@code email}, tên người dùng, v.v.) thay vì chỉ {@code id},
 * hãy xem xét sử dụng API <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html">Specifications</a>
 * của Spring Data JPA.
 */
public interface CrudService<T, ID> {

    /**
     * Lấy danh sách tất cả bản ghi trong cơ sở dữ liệu.
     * <p>
     * Kết quả không thể là {@code null}.
     *
     * @return {@link Result} chứa danh sách các bản ghi.
     */
    Result<?, Exception> findAll();

    /**
     * Lấy bản ghi theo {@code id}.
     *
     * @param id ID của bản ghi cần truy vấn, không được {@code null}.
     * @return {@link Result} chứa bản ghi nếu tồn tại, hoặc {@code null} nếu không.
     */
    default Result<?, Exception> findById(@NotNull ID id) {
        return null;
    }

    /**
     * Lưu một bản ghi vào cơ sở dữ liệu.
     *
     * @param body Đối tượng chứa dữ liệu bản ghi, không được {@code null}.
     * @return {@link Result} chứa bản ghi đã lưu.
     */
    Result<?, Exception> save(@NotNull T body);

    /**
     * Cập nhật bản ghi hiện có theo {@code id}.
     *
     * @param id ID của bản ghi cần cập nhật, không được {@code null}.
     * @param body Dữ liệu mới để cập nhật, không được {@code null}.
     * @return {@link Result} chứa bản ghi đã được cập nhật.
     */
    default Result<?, Exception> updateById(@NotNull ID id, @NotNull T body) {
        return null;
    }

    /**
     * Xóa bản ghi theo {@code id}.
     *
     * @param id ID của bản ghi cần xóa, không được {@code null}.
     * @return {@link Result} chứa kết quả thao tác xóa.
     */
    default Result<?, Exception> deleteById(@NotNull ID id) {
        return null;
    }
}