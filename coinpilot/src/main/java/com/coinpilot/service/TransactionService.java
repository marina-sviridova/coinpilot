package com.coinpilot.service;

import com.coinpilot.dto.CategorySumDTO;
import com.coinpilot.dto.TransactionPatchDTO;
import com.coinpilot.dto.TransactionRequestDTO;
import com.coinpilot.dto.TransactionResponseDTO;
import com.coinpilot.exception.WalletNotFoundException;
import com.coinpilot.mapper.TransactionMapper;
import com.coinpilot.model.Transaction;
import com.coinpilot.model.TransactionType;
import com.coinpilot.model.Wallet;
import com.coinpilot.repository.TransactionRepository;
import com.coinpilot.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final WalletRepository walletRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              TransactionMapper transactionMapper,
                              WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.walletRepository = walletRepository;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        Wallet wallet = walletRepository.findById(transactionRequestDTO.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(transactionRequestDTO.getWalletId()));
        return transactionMapper.transactionToResponseDTO(transactionRepository.save(
                transactionMapper.requestDTOtoTransaction(transactionRequestDTO, wallet)));
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::transactionToResponseDTO)
                .toList();
    }

    public Optional<TransactionResponseDTO> getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::transactionToResponseDTO);
    }

    public Optional<TransactionResponseDTO> updateTransactionById(Long id, TransactionRequestDTO transactionRequestDTO) {
        Wallet wallet = walletRepository.findById(transactionRequestDTO.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(transactionRequestDTO.getWalletId()));
        if (transactionRepository.existsById(id)) {
            Transaction updatedTransaction = transactionMapper.requestDTOtoTransaction(transactionRequestDTO, wallet);
            updatedTransaction.setId(id);
            return Optional.of(transactionMapper.transactionToResponseDTO(transactionRepository.save(updatedTransaction)));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteTransactionById(Long id) {
        boolean isTransactionExists = transactionRepository.existsById(id);
        if (isTransactionExists) {
            transactionRepository.deleteById(id);
        }
        return isTransactionExists;
    }

    public Optional<TransactionResponseDTO> patchTransactionById(Long id, TransactionPatchDTO transactionPatchDTO) {
        // Найти wallet только если walletId передан
        Optional<Wallet> wallet = transactionPatchDTO.getWalletId()
                .map(walletId -> walletRepository.findById(walletId)
                        .orElseThrow(() -> new WalletNotFoundException(walletId)));
        return transactionRepository.findById(id)
                .map(transaction -> transactionMapper.transactionPatchDtoToTransaction(transactionPatchDTO, transaction, wallet))
                .map(transaction -> transactionRepository.save(transaction))
                .map(transaction -> transactionMapper.transactionToResponseDTO(transaction));
    }

    public List<TransactionResponseDTO> findTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type).stream()
                .map(transaction -> transactionMapper.transactionToResponseDTO(transaction))
                .toList();
    }

    public List<TransactionResponseDTO> findTransactionsByDateBetween(LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByDateBetween(start, end).stream()
                .map(transaction -> transactionMapper.transactionToResponseDTO(transaction))
                .toList();
    }

    public List<TransactionResponseDTO> findTransactionsByTypeAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByTypeAndDateBetween(type, start, end).stream()
                .map(transaction -> transactionMapper.transactionToResponseDTO(transaction))
                .toList();
    }

    public BigDecimal getSumByTypeAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end) {
        return Optional.ofNullable(transactionRepository.sumByTypeAndDateBetween(type, start, end))
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getBalanceByDateBetween(LocalDateTime start, LocalDateTime end) {
        BigDecimal income = Optional.ofNullable(transactionRepository.sumByTypeAndDateBetween(TransactionType.INCOME, start, end))
                .orElse(BigDecimal.ZERO);
        BigDecimal expense = Optional.ofNullable(transactionRepository.sumByTypeAndDateBetween(TransactionType.EXPENSE, start, end))
                .orElse(BigDecimal.ZERO);
        return income.subtract(expense);
    }

    public List<CategorySumDTO> getSumByCategoriesAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end) {
        return transactionRepository.sumByCategoriesAndDateBetween(type, start, end);
    }
}