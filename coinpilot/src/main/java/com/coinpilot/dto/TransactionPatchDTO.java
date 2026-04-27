package com.coinpilot.dto;

import com.coinpilot.model.TransactionType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
@JsonDeserialize
public class TransactionPatchDTO {

    Optional<TransactionType> type = Optional.empty();
    Optional<BigDecimal> amount = Optional.empty();
    Optional<Currency> currency = Optional.empty();
    Optional<LocalDateTime> date = Optional.empty();
    Optional<String> category = Optional.empty();
    Optional<String> description = Optional.empty();
}