package com.demo.payment.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@RequiredArgsConstructor
public enum VNPayCurrency {
    VN("VND");

    @Getter
    String value;
}
