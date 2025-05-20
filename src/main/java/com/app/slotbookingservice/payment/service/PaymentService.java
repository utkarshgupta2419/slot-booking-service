package com.app.slotbookingservice.payment.service;

import com.app.slotbookingservice.payment.dto.CreateOrderDto;
import com.app.slotbookingservice.payment.dto.CreatedOrderResponse;
import com.app.slotbookingservice.payment.dto.MockRazorpayPaymentDto;
import com.app.slotbookingservice.payment.dto.VerifyPaymentDto;

public interface PaymentService {

    CreatedOrderResponse createPaymentOrder(CreateOrderDto orderDto) throws Exception;

    void verifyPayment(VerifyPaymentDto verifyPaymentDto) throws Exception;

    void registerPayment(MockRazorpayPaymentDto paymentDto) throws Exception;
}
