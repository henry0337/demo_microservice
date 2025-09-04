package com.demo.payment.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@RequiredArgsConstructor
public enum Command {
    PAY("pay"),
    QUERY("querydr"),
    REFUND("refund");

    @Getter
    String value;
}
