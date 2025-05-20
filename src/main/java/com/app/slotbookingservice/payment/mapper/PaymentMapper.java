package com.app.slotbookingservice.payment.mapper;

import com.app.slotbookingservice.payment.enums.PaymentStatus;
import com.app.slotbookingservice.payment.dto.CreateOrderDto;
import com.app.slotbookingservice.payment.dto.MockRazorpayPaymentDto;
import com.app.slotbookingservice.payment.entity.MockRazorpayPayment;
import com.app.slotbookingservice.payment.entity.PaymentHistory;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentHistory toPaymentHistory(final CreateOrderDto orderDto, final String orderId) {
        return PaymentHistory.builder()
                .orderId(orderId)
                .amount(orderDto.getAmount())
                .paymentStatus(PaymentStatus.IN_PROGRESS)
                .userEmail(orderDto.getUserEmail())
                .receiptId(orderDto.getReceiptId())
                .build();
    }

    public MockRazorpayPayment toMockRazorpayPayment(final MockRazorpayPaymentDto paymentDto) {
        return MockRazorpayPayment.builder()
                .paymentId(paymentDto.getPaymentId())
                .paymentStatus(paymentDto.getPaymentStatus())
                .amount(paymentDto.getAmount())
                .orderId(paymentDto.getOrderId())
                .signature(paymentDto.getSignature())
                .build();
    }
}
