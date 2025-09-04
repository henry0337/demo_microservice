package com.demo.payment.dto.response;

public record VNPayQueryResponse(
        String vnp_ResponseId,
        String vnp_TmnCode,
        String vnp_TxnRef,
        int vnp_Amount,
        String vnp_OrderInfo,
        int vnp_ResponseCode,
        String vnp_Message,
        String vnp_BankCode,
        String vnp_TransactionNo,
        String vnp_TransactionType,
        String vnp_TransactionStatus,
        String vnp_SecureHash
) { }
