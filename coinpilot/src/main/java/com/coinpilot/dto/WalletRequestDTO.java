package com.coinpilot.dto;

import com.coinpilot.model.WalletType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequestDTO {

    @NotBlank
    String name;
    @NotNull
    WalletType walletType;
    @NotNull
    Currency currency;
    @NotNull
    BigDecimal balance;
    String description;
}