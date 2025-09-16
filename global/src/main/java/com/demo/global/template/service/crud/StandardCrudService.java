package com.demo.global.template.service.crud;

import com.demo.global.template.service.crud.create.Insertable;
import com.demo.global.template.service.crud.delete.Deletable;
import com.demo.global.template.service.crud.read.ReadableWithID;
import com.demo.global.template.service.crud.update.Updatable;

/**
 * Giao diện cho các thao tác CRUD với {@link ID} là tiêu chí chính được sử dụng để tìm kiếm thực thể.
 * @param <T> Kiểu của thực thể
 * @param <ID> Kiểu của ID
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
public interface StandardCrudService<T, ID>
        extends Insertable<T>, ReadableWithID<T, ID>, Updatable<ID, T>, Deletable<ID> { }