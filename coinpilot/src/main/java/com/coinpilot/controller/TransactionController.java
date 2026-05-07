package com.coinpilot.controller;

import com.coinpilot.dto.CategorySumDTO;
import com.coinpilot.dto.TransactionPatchDTO;
import com.coinpilot.dto.TransactionRequestDTO;
import com.coinpilot.dto.TransactionResponseDTO;
import com.coinpilot.model.TransactionType;
import com.coinpilot.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO createdTransaction = transactionService.createTransaction(transactionRequestDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransactionById(@PathVariable Long id,
                                                                  @RequestBody TransactionRequestDTO transactionRequestDTO) {
        return new ResponseEntity<>(transactionService.updateTransactionById(id, transactionRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> patchTransactionById(@PathVariable Long id,
                                                                       @RequestBody TransactionPatchDTO transactionPatchDTO) {
        return new ResponseEntity<>(transactionService.patchTransactionById(id, transactionPatchDTO), HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end) {

        if (type != null && start != null && end != null) {
            return new ResponseEntity<>(transactionService.findTransactionsByTypeAndDateBetween(type, start, end), HttpStatus.OK);
        } else if (type != null) {
            return new ResponseEntity<>(transactionService.findTransactionsByType(type), HttpStatus.OK);
        } else if (start != null && end != null) {
            return new ResponseEntity<>(transactionService.findTransactionsByDateBetween(start, end), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
        }
    }

    @GetMapping("/transactions/summary/total")
    public ResponseEntity<BigDecimal> getSumByTypeAndDateBetween(@RequestParam TransactionType type,
                                                                 @RequestParam LocalDateTime start,
                                                                 @RequestParam LocalDateTime end) {
        return new ResponseEntity<>(transactionService.getSumByTypeAndDateBetween(type, start, end), HttpStatus.OK);
    }

    @GetMapping("/transactions/summary/balance")
    public ResponseEntity<BigDecimal> getBalanceByDateBetween(@RequestParam LocalDateTime start,
                                                              @RequestParam LocalDateTime end) {
        return new ResponseEntity<>(transactionService.getBalanceByDateBetween(start, end), HttpStatus.OK);
    }

    @GetMapping("/transactions/summary/categories")
    public ResponseEntity<List<CategorySumDTO>> getSumByCategoriesAndDateBetween(@RequestParam TransactionType type,
                                                                                 @RequestParam LocalDateTime start,
                                                                                 @RequestParam LocalDateTime end) {
        return new ResponseEntity<>(transactionService.getSumByCategoriesAndDateBetween(type, start, end), HttpStatus.OK);
    }
}