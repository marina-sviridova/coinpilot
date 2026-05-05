package com.coinpilot.dto;

import com.coinpilot.model.WalletType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletResponseDTO {

    Long id;
    String name;
    WalletType walletType;
    Currency currency;
    BigDecimal balance;
    String description;
}