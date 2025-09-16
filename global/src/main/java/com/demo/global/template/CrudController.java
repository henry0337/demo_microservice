package com.demo.global.template;

import org.springframework.http.ResponseEntity;

public interface CrudController<T, C> {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findByCriteria(C criteria);
    ResponseEntity<?> save(T body);
    ResponseEntity<?> update(C criteria, T body);
    ResponseEntity<?> delete(C criteria);
}
