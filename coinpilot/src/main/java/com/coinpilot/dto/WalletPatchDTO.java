package com.coinpilot.dto;

import com.coinpilot.model.WalletType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
public class WalletPatchDTO {

    Optional<String> name = Optional.empty();
    Optional<WalletType> walletType = Optional.empty();
    Optional<Currency> currency = Optional.empty();
    Optional<BigDecimal> balance = Optional.empty();
    Optional<String> description = Optional.empty();
}