package com.coinpilot.exception;

import java.util.Currency;

public class CurrencyMismatchException extends RuntimeException {
    public CurrencyMismatchException(Currency walletCurrency, Currency transactionCurrency) {
        super("Currency mismatch: wallet currency is " + walletCurrency + " but transaction currency is " + transactionCurrency);
    }
}