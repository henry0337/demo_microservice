package com.demo.payment.service;

import com.demo.payment.dto.response.VNPayQueryResponse;
import com.demo.payment.model.*;
import com.demo.payment.repository.PaymentRepository;
import com.demo.payment.utility.VNPayUtil;
import com.demo.global.utility.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Moineau
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VNPayService {
    PaymentRepository repository;
    ObjectMapper mapper;
    RestTemplate template;

    @Value("${server.port}")
    @NonFinal
    String port;

    @Value("${vnpay.tmnCode}")
    @NonFinal
    String vnp_TmnCode;

    @Value("${vnpay.secureSecret}")
    @NonFinal
    String secureSecret;

    /**
     * Khởi tạo URL thanh toán cho đơn giao dịch với các tham số của <b>VNPay</b> cung cấp.
     * <p>
     * Chức năng được triển khai là chức năng <b>xây dựng URL thanh toán</b> trong VNPay.
     * @param amount   Số tiền thực hiện giao dịch
     * @param ipAddr   Địa chỉ IP của đối tượng thực hiện giao dịch
     * @param bankCode Loại hình thanh toán, có thể {@code null}
     * @return Một URL điều hướng tới trang web thực hiện thanh toán của VNPay.
     */
    public String createPaymentUrl(
            int amount,
            String ipAddr,
            @Nullable String bankCode
    ) {
        final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"; // URL thanh toán gốc
        final String vnp_Version = "2.1.0"; // Phiên bản của thư viện VNPay
        final Command vnp_Command = Command.PAY; // Mã API của VNPay
        final OrderType vnp_OrderType = OrderType.OTHER; // Mã danh mục hàng hóa
        final VNPayCurrency vnp_CurrCode = VNPayCurrency.VN; // Đơn vị tiền tệ
        final VNPayLocale vnp_Locale = VNPayLocale.VN; // Ngôn ngữ giao diện
        final String vnp_TxnRef = StringUtils.generateRandomNumberAsString(8); // Mã tham chiếu của giao dịch
        final String vnp_OrderInfo = "Thanh toan don hang: "; // Mô tả thông tin đơn hàng
        final String vnp_ReturnUrl = "http://localhost:" + port + "/api/v1/payment/vnpay/vnpay-return";

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        var dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        final String vnp_CreateDate = dateFormatter.format(calendar.getTime());

        calendar.add(Calendar.MINUTE, 15);
        final String vnp_ExpireDate = dateFormatter.format(calendar.getTime());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command.getValue());
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode.getValue());
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", vnp_OrderType.getValue());
        vnp_Params.put("vnp_Locale", vnp_Locale.getValue());
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        if (bankCode != null && !bankCode.isEmpty()) vnp_Params.put("vnp_BankCode", bankCode);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        Iterator<String> iterator = fieldNames.iterator();
        var hashedData = new StringBuilder();
        var query = new StringBuilder();

        while (iterator.hasNext()) {
            final String fieldKey = iterator.next();
            final String fieldValue = vnp_Params.get(fieldKey);

            if (!fieldValue.isEmpty()) {
                hashedData.append(fieldNames);
                hashedData.append('=');
                hashedData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                query.append(URLEncoder.encode(fieldKey, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (iterator.hasNext()) {
                    query.append('&');
                    hashedData.append('&');
                }
            }
        }

        final String vnp_SecureHash = VNPayUtil.generateHMACSHA512(secureSecret, hashedData.toString());
        String queryUrl = query.toString();
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return vnp_PayUrl + '?' + queryUrl;
    }

    public boolean verifyPaymentRequest(
            @NonNull Map<String, String> fields,
            String secureHash,
            String responseCode
    ) {
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        String signedValue = VNPayUtil.hashAllFields(fields);

        if (signedValue.equals(secureHash) && "00".equals(responseCode)) {
            var newBill = new Payment();

            newBill.setOrderId();
            return true;
        }


    }

    public Payment getBillingInformation(String orderId, String dateAsString, String ipAddr) {
        final String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
        final String vnp_RequestId = StringUtils.generateRandomNumberAsString(8);
        final String vnp_Version = "2.1.0";
        final Command vnp_Command = Command.QUERY;
        final @SuppressWarnings("unused") String vnp_TxnRef = orderId;
        final String vnp_OrderInfo = "Thanh toan don hang: " + vnp_TxnRef;
        final @SuppressWarnings("unused") String vnp_TransactionDate = dateAsString;
        final @SuppressWarnings("unused") String vnp_IpAddr = ipAddr;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        var formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        final String vnp_CreateDate = formatter.format(calendar.getTime());

        final String hashedData = String.join("|",
                vnp_RequestId,
                vnp_Version,
                vnp_Command.getValue(),
                vnp_TmnCode,
                vnp_TxnRef,
                vnp_TransactionDate,
                vnp_CreateDate,
                vnp_IpAddr,
                vnp_OrderInfo
        );

        final String vnp_SecureHash = VNPayUtil.generateHMACSHA512(secureSecret, hashedData);

        ObjectNode vnp_Params = mapper.createObjectNode();
        vnp_Params.put("vnp_RequestId", vnp_RequestId);
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command.getValue());
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_TransactionDate", vnp_TransactionDate);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ObjectNode> entity = new HttpEntity<>(vnp_Params, headers);
        String request = template.postForEntity(vnp_ApiUrl, entity, String.class).getBody();
        Object result;
        try {
            result = mapper.readValue(request, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (result != null) {
            final var actualResult = (VNPayQueryResponse) result;
        }
    }
}
