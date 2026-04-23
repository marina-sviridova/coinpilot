package com.coinpilot.dto;

import com.coinpilot.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {

    @NotNull
    TransactionType type;
    @Positive
    @NotNull
    BigDecimal amount;
    @NotNull
    Currency currency;
    @NotNull
    LocalDateTime date;
    @NotBlank
    String category;
    String description;
}