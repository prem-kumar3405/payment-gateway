package com.paymentgateway.exception;

public class PaymentNotProcessableException extends RuntimeException {

    public PaymentNotProcessableException(String message) {
        super(message);
    }
}