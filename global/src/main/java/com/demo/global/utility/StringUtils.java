package com.demo.global.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Lớp công cụ chứa các hàm giúp xử lý chuỗi.
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {
    private static final Random rnd = new Random();

    /**
     * Khởi tạo một số ngẫu nhiên với độ dài {@code length} dưới dạng là một <b>chuỗi</b>.
     * @param length Độ dài của số (chuỗi) đó
     * @return Một số ngẫu nhiên ở dạng chuỗi.
     */
    @NotNull
    public static String generateRandomNumberAsString(int length) {
        String chars = "0123456789";
        var sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
