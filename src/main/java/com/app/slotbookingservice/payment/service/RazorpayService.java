package com.app.slotbookingservice.payment.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class RazorpayService {

    private final RazorpayClient razorpayClient;
    @Value("${razorpay.key}")
    private String key;
    @Value("${razorpay.secret}")
    private String secret;

    public RazorpayService(@Value("${razorpay.key}") String key,
                           @Value("${razorpay.secret}") String secret) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(key, secret);
    }

    public String createOrder(int amount, String currency, String receiptId) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", currency);
        options.put("receipt", receiptId);
        options.put("payment_capture", true);
        Order order = razorpayClient.orders.create(options);
        return order.toString();
    }

    public boolean verifyPayment(String orderId, String paymentId, String signature) {
        try {
            String data = orderId + "|" + paymentId;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);

            byte[] hash = sha256_HMAC.doFinal(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            String generatedSignature = hexString.toString();
            return generatedSignature.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }


}
