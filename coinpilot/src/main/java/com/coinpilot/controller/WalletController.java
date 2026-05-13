package com.coinpilot.controller;

import com.coinpilot.dto.TransactionResponseDTO;
import com.coinpilot.dto.WalletRequestDTO;
import com.coinpilot.dto.WalletResponseDTO;
import com.coinpilot.service.TransactionService;
import com.coinpilot.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class WalletController {

    private final WalletService walletService;
    private final TransactionService transactionService;

    public WalletController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<WalletResponseDTO> createWallet(@Valid @RequestBody WalletRequestDTO walletRequestDTO) {
        return new ResponseEntity<>(walletService.createWallet(walletRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletResponseDTO> getWalletById(@PathVariable Long id) {
        return new ResponseEntity<>(walletService.getWalletById(id), HttpStatus.OK);
    }

    @PutMapping("/wallets/{id}")
    public ResponseEntity<WalletResponseDTO> updateWalletById(@PathVariable Long id, @Valid @RequestBody WalletRequestDTO walletRequestDTO) {
        return new ResponseEntity<>(walletService.updateWalletById(id, walletRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<Void> deleteWalletById(@PathVariable Long id) {
        walletService.deleteWalletById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/wallets/{id}/balance")
    public ResponseEntity<BigDecimal> getWalletBalance(@PathVariable Long id) {
        return new ResponseEntity<>(walletService.getWalletBalance(id), HttpStatus.OK);
    }

    @GetMapping("/wallets/{id}/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByWallet(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.getTransactionsByWalletId(id), HttpStatus.OK);
    }
}