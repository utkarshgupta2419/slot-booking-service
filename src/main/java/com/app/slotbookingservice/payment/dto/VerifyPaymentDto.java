package com.app.slotbookingservice.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyPaymentDto {

    @NotBlank(message = "Payment ID must not be blank")
    private String paymentId;

    @NotBlank(message = "Order ID must not be blank")
    private String orderId;

    @NotBlank(message = "Signature must not be blank")
    private String signature;

}
