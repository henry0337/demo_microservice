package com.demo.global.template.service.crud;

import com.demo.global.template.service.crud.create.Insertable;
import com.demo.global.template.service.crud.delete.Deletable;
import com.demo.global.template.service.crud.read.ReadableWithCriteria;
import com.demo.global.template.service.crud.update.Updatable;

/**
 * Giao diện cho các thao tác CRUD với {@link C} là tiêu chí chính được sử dụng để tìm kiếm thực thể.
 * @param <T> Kiểu của thực thể sẽ được dùng để tương tác
 * @param <C> Kiểu của tiêu chí sẽ được dùng để tìm kiếm
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
public interface CustomCrudService<T, C>
        extends Insertable<T>, ReadableWithCriteria<T, C>, Updatable<C, T>, Deletable<C> { }
