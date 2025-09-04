package com.demo.global.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {
    private static final Random rnd = new Random();

    /**
     * Khởi tạo một số ngẫu nhiên với độ dài {@code length} dưới dạng là một {@linkplain String chuỗi}.
     * @param length Độ dài của số (chuỗi) đó
     * @return Một số ngẫu nhiên ở dạng chuỗi.
     * @author Moineau
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

    /**
     * Kiểm tra chuỗi có rỗng hay không,
     * bao gồm các tiêu chí kiểm tra là: {@code null}, rỗng hoặc chỉ chứa khoảng trắng.
     *
     * @param str Chuỗi cần kiểm tra.
     * @return {@code true} nếu chuỗi {@code null}, rỗng hoặc chỉ chứa khoảng trắng.
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Kiểm tra liệu chuỗi cần được kiểm tra đang không rỗng.
     *
     * @param str Chuỗi cần kiểm tra.
     * @return true nếu chuỗi có giá trị.
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Chuẩn hóa chuỗi: loại bỏ khoảng trắng thừa, chuyển về chữ thường.
     *
     * @param str Chuỗi đầu vào.
     * @return Chuỗi đã chuẩn hóa, hoặc null nếu đầu vào là null.
     */
    @Nullable
    public static String normalize(String str) {
        if (isBlank(str)) return null;
        return str.trim().toLowerCase();
    }

    /**
     * Đảo ngược chuỗi.
     *
     * @param str Chuỗi cần đảo ngược.
     * @return Chuỗi đã đảo ngược, hoặc null nếu đầu vào là null.
     */
    @Nullable
    public static String reverse(String str) {
        if (isBlank(str)) return null;
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Viết hoa chữ cái đầu mỗi từ trong chuỗi.
     *
     * @param str Chuỗi đầu vào.
     * @return Chuỗi với chữ cái đầu mỗi từ được viết hoa, hoặc null nếu đầu vào là null.
     */
    @Nullable
    public static String capitalizeWords(String str) {
        if (isBlank(str)) return null;
        String[] words = str.trim().split("\\s+");
        var result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return result.toString().trim();
    }

    /**
     * Cắt chuỗi thành mảng các từ, bỏ khoảng trắng thừa.
     *
     * @param str Chuỗi đầu vào.
     * @return Mảng các từ, hoặc mảng rỗng nếu đầu vào là null hoặc rỗng.
     */
    public static String @NotNull [] splitWords(String str) {
        if (isBlank(str)) return new String[0];
        return str.trim().split("\\s+");
    }

    /**
     * Thay thế tất cả các ký tự đặc biệt (ngoài chữ cái và số) bằng một ký tự thay thế.
     *
     * @param str        Chuỗi đầu vào.
     * @param replacement Ký tự thay thế.
     * @return Chuỗi đã thay thế, hoặc null nếu đầu vào là null.
     */
    @Nullable
    public static String replaceSpecialCharacters(String str, char replacement) {
        if (isBlank(str)) return null;
        return str.replaceAll("[^a-zA-Z0-9]", String.valueOf(replacement));
    }

    /**
     * Đếm số lần xuất hiện của một chuỗi con trong chuỗi chính.
     *
     * @param str    Chuỗi chính.
     * @param subStr Chuỗi con cần đếm.
     * @return Số lần xuất hiện, hoặc 0 nếu một trong hai chuỗi là null hoặc rỗng.
     */
    public static int countOccurrences(String str, String subStr) {
        if (isBlank(str) || isBlank(subStr)) return 0;
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }
        return count;
    }
}
