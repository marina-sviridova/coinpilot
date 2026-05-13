package com.coinpilot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue
    Long id;
    String name;
    @Enumerated(EnumType.STRING)
    WalletType walletType;
    Currency currency;
    BigDecimal balance;
    String description;
}