package com.app.slotbookingservice.exception;

import com.app.slotbookingservice.exception.CustomExceptions.BookingRegistrationException;
import com.app.slotbookingservice.exception.CustomExceptions.RazorpayOrderCreationException;
import com.app.slotbookingservice.exception.CustomExceptions.RazorpayPaymentVerificationException;
import com.app.slotbookingservice.exception.CustomExceptions.UserRegistrationException;
import com.app.slotbookingservice.utlis.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    public ProblemDetail handleUserRegistrationException(final UserRegistrationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(AppConstants.USER_REGISTER_FAILURE_MSG);
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(BookingRegistrationException.class)
    public ProblemDetail handleBookingRegistrationException(final BookingRegistrationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(AppConstants.BOOKING_REGISTER_FAILURE_MSG);
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(RazorpayOrderCreationException.class)
    public ProblemDetail handleRazorpayOrderCreationException(final RazorpayOrderCreationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(AppConstants.RAZORPAY_ORDER_CREATION_EXC_MSG);
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(RazorpayPaymentVerificationException.class)
    public ProblemDetail handleRazorpayPaymentVerificationException(final RazorpayPaymentVerificationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle(AppConstants.RAZORPAY_PAYMENT_VERIFICATION_EXC_MSG);
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail genericException(final Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle(AppConstants.UNKNOWN_ERROR);
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

}
