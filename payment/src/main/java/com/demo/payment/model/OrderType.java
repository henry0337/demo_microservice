package com.demo.payment.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@RequiredArgsConstructor
public enum OrderType {
    OTHER("other");

    @Getter
    String value;
}
