package com.app.slotbookingservice.exception.CustomExceptions;

public class RazorpayPaymentVerificationException extends RuntimeException {

    public RazorpayPaymentVerificationException(String message) {
        super(message);
    }
}
