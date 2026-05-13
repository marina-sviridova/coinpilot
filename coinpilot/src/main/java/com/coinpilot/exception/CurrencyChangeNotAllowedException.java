package com.coinpilot.exception;

import java.util.Currency;

public class CurrencyChangeNotAllowedException extends RuntimeException {

    public CurrencyChangeNotAllowedException(Currency walletCurrency, Currency newWalletCurrency) {
        super("Changing wallet currency from " + walletCurrency + " to " + newWalletCurrency + " not allowed.");
    }
}