package com.app.slotbookingservice.payment.controller;

import com.app.slotbookingservice.common.AbstractController;
import com.app.slotbookingservice.payment.dto.CreateOrderDto;
import com.app.slotbookingservice.payment.dto.MockRazorpayPaymentDto;
import com.app.slotbookingservice.payment.dto.VerifyPaymentDto;
import com.app.slotbookingservice.payment.service.PaymentService;
import com.app.slotbookingservice.utlis.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController extends AbstractController {

    private final PaymentService paymentService;

    @PostMapping("/order/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderDto orderDto) throws Exception {
        return generateResponse(paymentService.createPaymentOrder(orderDto), HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@Valid @RequestBody VerifyPaymentDto verifyPaymentDto) throws Exception {
        paymentService.verifyPayment(verifyPaymentDto);
        return generateResponse(AppConstants.RAZORPAY_PAYMENT_VERIFIED, HttpStatus.OK);
    }

    //TO Manually register payment in Mongo for Razorpay
    @PostMapping("/register")
    public ResponseEntity<?> registerPayment(@Valid @RequestBody MockRazorpayPaymentDto paymentDto) throws Exception {
        paymentService.registerPayment(paymentDto);
        return generateResponse(AppConstants.RAZORPAY_PAYMENT_REGISTERED, HttpStatus.CREATED);
    }

}
