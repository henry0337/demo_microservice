package com.demo.payment.controller;

import com.demo.payment.service.VNPayService;
import com.demo.global.annotation.ApiController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
@ApiController("/api/v1/payment/vnpay")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VNPayController {
    VNPayService service;

    @PostMapping("vnpay-return")
    public ResponseEntity<Boolean> verifyPaymentRequest(@NonNull HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                fields.put(fieldName, fieldValue);
            }
        }

        final String secureHash = request.getParameter("vnp_SecureHash");
        final String responseCode = request.getParameter("vnp_ResponseCode");

        boolean isValid = service.verifyPaymentRequest(fields, secureHash, responseCode);
        return isValid ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    private String obtainCurrentIp(@NonNull HttpServletRequest request) {
        String forwardedHeader = request.getHeader("X-Forwarded-For");
        if (forwardedHeader != null) return request.getRemoteAddr();
        return "";
    }
}
