package com.coinpilot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id @GeneratedValue
    Long id;
    @ManyToOne
    Wallet wallet;
    @Enumerated(EnumType.STRING)
    TransactionType type;
    BigDecimal amount;
    Currency currency;
    LocalDateTime date;
    // Valid values defined in IncomeCategory and ExpenseCategory enums
    String category;
    String description;
}