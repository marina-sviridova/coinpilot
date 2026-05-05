package com.coinpilot.service;

import com.coinpilot.dto.WalletRequestDTO;
import com.coinpilot.dto.WalletResponseDTO;
import com.coinpilot.mapper.WalletMapper;
import com.coinpilot.model.Wallet;
import com.coinpilot.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<WalletResponseDTO> getWalletById(Long id) {
        return walletRepository.findById(id)
                .map(walletMapper::walletToResponseDto);
    }

    public Optional<WalletResponseDTO> updateWalletById(Long id, WalletRequestDTO walletRequestDTO) {
        if (walletRepository.existsById(id)) {
            Wallet updatedWallet = walletMapper.requestDtoToWallet(walletRequestDTO);
            updatedWallet.setId(id);
            return Optional.of(walletMapper.walletToResponseDto(walletRepository.save(updatedWallet)));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteWalletById(Long id) {
        boolean isWalletExists = walletRepository.existsById(id);
        if (isWalletExists) {
            walletRepository.deleteById(id);
        }
        return isWalletExists;
    }
}