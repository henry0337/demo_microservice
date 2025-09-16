package com.demo.user.repository;

import com.demo.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> { }