package com.demo.payment.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author <a href="https://vnpay.vn/">CTT VNPAY</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VNPayUtil {

    @Value("${vnpay.secureSecret}")
    private static String secureSecret;

    public static String generateMd5(@NotNull String message) {
        String digest;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
            var sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }

        return digest;
    }

    public static String generateSHA256(@NotNull String message) {
        String digest;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
            var sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }

        return digest;
    }

    @NotNull
    public static String hashAllFields(@NotNull Map<String, String> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);

        var sb = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return generateHMACSHA512(secureSecret, sb.toString());
    }

    @NotNull
    public static String generateHMACSHA512(final String key, final String data) {
        if (key == null || data == null) throw new NullPointerException();

        try {
            final var hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();

            final var secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);

            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            final var sb = new StringBuilder(2 * result.length);

            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
