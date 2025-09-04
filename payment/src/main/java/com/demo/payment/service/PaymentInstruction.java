package com.demo.payment.service;

import com.demo.payment.model.Payment;

/**
 * Giao diện chứa các phương thức dùng để xử lý <b>thanh toán</b>.
 * @author Moineau
 */
public interface PaymentInstruction {
    String createPaymentUrl();

    boolean verifyPaymentRequest();

    Payment getBillingInformation();
}
