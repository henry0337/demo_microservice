package com.demo.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class VerifyRequest {
    private Map<String, String> fields;
    private String secureHash;
    private String responseCode;
}