package com.coinpilot.mapper;

import com.coinpilot.dto.WalletRequestDTO;
import com.coinpilot.dto.WalletResponseDTO;
import com.coinpilot.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public Wallet requestDtoToWallet(WalletRequestDTO walletRequestDTO) {
        return Wallet.builder()
                .name(walletRequestDTO.getName())
                .walletType(walletRequestDTO.getWalletType())
                .currency(walletRequestDTO.getCurrency())
                .balance(walletRequestDTO.getBalance())
                .description(walletRequestDTO.getDescription())
                .build();
    }

    public WalletResponseDTO walletToResponseDto(Wallet wallet) {
        return WalletResponseDTO.builder()
                .id(wallet.getId())
                .name(wallet.getName())
                .walletType(wallet.getWalletType())
                .currency(wallet.getCurrency())
                .balance(wallet.getBalance())
                .description(wallet.getDescription())
                .build();
    }
}