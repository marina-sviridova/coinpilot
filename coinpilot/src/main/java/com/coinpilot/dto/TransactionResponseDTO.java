package com.coinpilot.dto;

import com.coinpilot.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {

    Long id;
    TransactionType type;
    BigDecimal amount;
    Currency currency;
    LocalDateTime date;
    String category;
    String description;
}