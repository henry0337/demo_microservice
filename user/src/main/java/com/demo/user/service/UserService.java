package com.demo.user.service;

import com.demo.auth.model.User;
import com.demo.global.service.CrudService;
import com.demo.user.repository.UserRepository;
import com.demo.user.repository.UserSpecification;
import com.demo.global.helper.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements CrudService<User, String> {
    UserRepository      repository;
    PasswordEncoder     encoder;
    UserSpecification   specification;

    @NonNull
    public Result<List<User>, Exception> findAll() {
        List<User> result = repository.findAll();
        return result.isEmpty()
                ? new Result.Success<>(204, List.of())
                : new Result.Success<>(200, result);
    }

    public Result<User, Exception> findByEmail(String email) {
        Optional<User> user = repository.findOne(specification.findByCriteria("email", email));
        return user.<Result<User, Exception>>map(value ->
                        new Result.Success<>(200, value))
                .orElseGet(() -> new Result.Success<>(204, null));
    }

    public Result<?, Exception> save(@NonNull User user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            User savedUser = repository.save(user);
            return new Result.Success<>(201, savedUser);
        } catch (Exception e) {
            return new Result.Failure<>(400, e);
        }
    }

    public Result<?, Exception> update(String email, User user) {
        try {
            Optional<User> currentUser = repository.findOne(specification.findByCriteria("email", email));
            if (currentUser.isEmpty()) throw new NoSuchElementException("Không có bản ghi nào thuộc email này!");

            User newUser = currentUser.get();
            newUser.setName(user.getName());
            newUser.setPassword(encoder.encode(user.getPassword()));
            newUser.setAvatar(user.getAvatar());
            newUser.setRole(user.getRole());
            newUser.setIsAccountExpired(user.getIsAccountExpired());
            newUser.setIsCredentialsExpired(user.getIsCredentialsExpired());
            newUser.setIsAccountLocked(user.getIsAccountLocked());

            return new Result.Success<>(200, repository.save(newUser));
        } catch (Exception e) {
            return new Result.Failure<>(400, e);
        }
    }

    public Result<?, Exception> deleteByEmail(String email) {
        try {
            long deletedCount = repository.delete(specification.findByCriteria("email", email));
            if (deletedCount == 1) {
                return new Result.Success<>(200, "Đã xóa thành công bản ghi!");
            } else {
                return new Result.Failure<>(400, new RuntimeException("Có lỗi xảy ra khi xóa bản ghi!"));
            }
        } catch (Exception e) {
            return new Result.Failure<>(500, e);
        }
    }
}

