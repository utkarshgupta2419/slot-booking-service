package com.app.slotbookingservice.payment.entity;

import com.app.slotbookingservice.payment.enums.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is to Mock Razorpay Payment data
 */
@Document(collection = "MockRazorpayPayment")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MockRazorpayPayment {

    @NotBlank(message = "Payment ID must not be blank")
    private String paymentId;

    @NotBlank(message = "Order ID must not be blank")
    private String orderId;

    @Positive(message = "Amount must be greater than 0")
    private int amount;

    @NotNull(message = "Payment status must not be null")
    private PaymentStatus paymentStatus;

    @NotBlank(message = "Signature must not be blank")
    private String signature;

}
