package com.coinpilot.service;

import com.coinpilot.dto.WalletRequestDTO;
import com.coinpilot.dto.WalletResponseDTO;
import com.coinpilot.exception.WalletNotFoundException;
import com.coinpilot.mapper.WalletMapper;
import com.coinpilot.model.Wallet;
import com.coinpilot.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public WalletService(WalletRepository walletRepository, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }

    public WalletResponseDTO createWallet(WalletRequestDTO walletRequestDTO) {
        return walletMapper.walletToResponseDto(walletRepository.save(
                walletMapper.requestDtoToWallet(walletRequestDTO)));
    }

    public WalletResponseDTO getWalletById(Long id) {
        return walletRepository.findById(id)
                .map(walletMapper::walletToResponseDto)
                .orElseThrow(() -> new WalletNotFoundException(id));
    }

    public WalletResponseDTO updateWalletById(Long id, WalletRequestDTO walletRequestDTO) {
        walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        Wallet updatedWallet = walletMapper.requestDtoToWallet(walletRequestDTO);
        updatedWallet.setId(id);
        return walletMapper.walletToResponseDto(walletRepository.save(updatedWallet));
    }

    public void deleteWalletById(Long id) {
        walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        walletRepository.deleteById(id);
    }

    public BigDecimal getWalletBalance(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id))
                .getBalance();
    }
}