package com.coinpilot.mapper;

import com.coinpilot.dto.WalletPatchDTO;
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

    public Wallet walletPatchDtoToWallet(WalletPatchDTO walletPatchDTO, Wallet wallet) {
        walletPatchDTO.getName()
                .ifPresent(name -> wallet.setName(name));
        walletPatchDTO.getWalletType()
                .ifPresent(walletType -> wallet.setWalletType(walletType));
        walletPatchDTO.getBalance()
                .ifPresent(balance -> wallet.setBalance(balance));
        walletPatchDTO.getDescription()
                .ifPresent(description -> wallet.setDescription(description));
        return wallet;
    }
}