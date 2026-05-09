package com.coinpilot.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(BigDecimal balance, BigDecimal amount) {
        super("Insufficient funds: wallet balance is " + balance + " but transaction amount is " + amount);
    }
}