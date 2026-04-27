package com.coinpilot.controller;

import com.coinpilot.dto.TransactionPatchDTO;
import com.coinpilot.dto.TransactionRequestDTO;
import com.coinpilot.dto.TransactionResponseDTO;
import com.coinpilot.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        List<TransactionResponseDTO> receivedTransactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(receivedTransactions, HttpStatus.OK);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id).
                map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransactionById(@PathVariable Long id,
                                                                  @RequestBody TransactionRequestDTO transactionRequestDTO) {
        return transactionService.updateTransactionById(id, transactionRequestDTO)
                .map(transactionResponseDTO -> new ResponseEntity<>(transactionResponseDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long id) {
        if (transactionService.deleteTransactionById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> patchTransactionById(@PathVariable Long id,
                                                                       @RequestBody TransactionPatchDTO transactionPatchDTO) {
        return transactionService.patchTransactionById(id, transactionPatchDTO)
                .map(transactionResponseDTO -> new ResponseEntity<>(transactionResponseDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}