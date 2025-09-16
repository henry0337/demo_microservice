package com.demo.user.controller;

import com.demo.auth.model.User;
import com.demo.user.service.UserService;
import com.demo.global.annotation.ApiController;
import com.demo.global.helper.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@ApiController("/api/v1/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    /**
     * Tạo mới một người dùng.
     *
     * @param user Đối tượng người dùng cần tạo, không được null.
     * @return {@link ResponseEntity} với mã trạng thái 201 và thông tin người dùng mới nếu thành công,
     * hoặc mã 400 kèm thông báo lỗi nếu thất bại.
     * @throws IllegalArgumentException nếu đối tượng user là null.
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @NonNull User user) {
        Result<?, Exception> newUser = service.save(user);

        if (newUser instanceof Result.Success<?>) {
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(newUser);
        } else if (newUser instanceof Result.Failure<?, ?>) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(newUser);
        }
        return ResponseEntity.internalServerError().body("Lỗi không xác định!");
    }

    /**
     * Cập nhật thông tin người dùng dựa trên email.
     *
     * @param email Email của người dùng cần cập nhật, không được {@code null}.
     * @param user  Đối tượng chứa thông tin người dùng cập nhật.
     * @return {@link ResponseEntity} với mã trạng thái 200 và thông tin người dùng đã cập nhật nếu thành công,
     * hoặc mã 400 kèm thông báo lỗi nếu thất bại.
     * @throws IllegalArgumentException nếu email hoặc user là null.
     */
    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable @NonNull String email, @RequestBody User user) {
        if (user == null) return ResponseEntity.badRequest().build();

        Result<?, Exception> updatedUser = service.update(email, user);
        if (updatedUser instanceof Result.Success<?>) {
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(updatedUser);
        } else if (updatedUser instanceof Result.Failure<?, ?>) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(updatedUser);
        }

        return ResponseEntity.internalServerError().body("Lỗi không xác định!");
    }

    /**
     * Xóa người dùng dựa trên email.
     *
     * @param email Email của người dùng cần xóa.
     * @return {@link ResponseEntity} với mã trạng thái 200 và thông báo thành công nếu xóa thành công,
     * hoặc mã 400 kèm thông báo lỗi nếu thất bại.
     */
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        Result<?, Exception> recordDeleted = service.delete(email);
        if (recordDeleted.getCode() == 200) {
            return ResponseEntity.ok("Đã xóa bản ghi thành công");
        } else {
            return ResponseEntity.badRequest().body("Lỗi không xác định!");
        }
    }
}
