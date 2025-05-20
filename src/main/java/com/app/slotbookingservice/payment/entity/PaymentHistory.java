package com.app.slotbookingservice.payment.entity;

import com.app.slotbookingservice.common.AbstractEntity;
import com.app.slotbookingservice.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "PaymentHistory")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentHistory extends AbstractEntity {

    @Field(name = "order_id")
    private String orderId;

    @Field(name = "payment_id")
    private String paymentId;

    @Field(name = "receipt_id")
    private String receiptId;

    @Field(name = "user_email")
    private String userEmail;

    @Field(name = "amount")
    private int amount;

    @Field(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Field(name = "payment_failure_msg")
    private String paymentFailureMsg;


}
