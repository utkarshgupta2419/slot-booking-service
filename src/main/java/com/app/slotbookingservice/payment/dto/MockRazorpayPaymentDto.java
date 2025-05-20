package com.app.slotbookingservice.payment.dto;

import com.app.slotbookingservice.payment.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MockRazorpayPaymentDto {

    private String paymentId;
    private String orderId;
    private int amount;
    private PaymentStatus paymentStatus;
    private String signature;

}
