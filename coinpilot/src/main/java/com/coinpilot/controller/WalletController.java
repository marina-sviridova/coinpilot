package com.coinpilot.controller;

import com.coinpilot.dto.WalletRequestDTO;
import com.coinpilot.dto.WalletResponseDTO;
import com.coinpilot.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<WalletResponseDTO> createWallet(@RequestBody WalletRequestDTO walletRequestDTO) {
        return new ResponseEntity<>(walletService.createWallet(walletRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletResponseDTO> getWalletById(@PathVariable Long id) {
        return walletService.getWalletById(id)
                .map(walletResponseDTO -> new ResponseEntity<>(walletResponseDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/wallets/{id}")
    public ResponseEntity<WalletResponseDTO> updateWalletById(@PathVariable Long id, @RequestBody WalletRequestDTO walletRequestDTO) {
        return walletService.updateWalletById(id, walletRequestDTO)
                .map(walletResponseDTO -> new ResponseEntity<>(walletResponseDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<Void> deleteWalletById(@PathVariable Long id) {
        if (walletService.deleteWalletById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}