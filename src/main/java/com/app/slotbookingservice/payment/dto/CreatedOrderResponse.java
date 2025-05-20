package com.app.slotbookingservice.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatedOrderResponse {

    private String orderId;
    private int amount;
    private String currency;
    private String status;
    private String receiptId;
}
