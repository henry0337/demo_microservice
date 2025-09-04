package com.demo.payment.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@RequiredArgsConstructor
public enum BankCode {
    QR_CODE("VNPAYQR"),
    BANK("VNBANK"),
    INTERNATIONAL_CARD("INTCARD");

    @Getter
    String value;
}
