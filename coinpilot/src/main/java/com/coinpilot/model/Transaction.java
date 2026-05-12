package com.coinpilot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    TransactionType type;
    BigDecimal amount;
    Currency currency;
    LocalDateTime date;
    // Valid values defined in IncomeCategory and ExpenseCategory enums
    String category;
    String description;
}