package com.coinpilot.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(Long id) {
        super("Wallet not found with id: " + id);
    }
}