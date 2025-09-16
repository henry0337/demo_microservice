package com.demo.user.repository;

import com.demo.auth.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Component
public class UserSpecification {

    @NonNull
    public Specification<User> findByCriteria(String fieldName, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), value);
    }
}
