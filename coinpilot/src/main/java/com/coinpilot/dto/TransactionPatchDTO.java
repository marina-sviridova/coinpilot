package com.coinpilot.dto;

import com.coinpilot.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPatchDTO {

    Optional<Long> walletId = Optional.empty();
    Optional<TransactionType> type = Optional.empty();
    Optional<BigDecimal> amount = Optional.empty();
    Optional<Currency> currency = Optional.empty();
    Optional<LocalDateTime> date = Optional.empty();
    Optional<String> category = Optional.empty();
    Optional<String> description = Optional.empty();
}