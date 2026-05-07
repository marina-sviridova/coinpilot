package com.coinpilot.service;

import com.coinpilot.dto.CategorySumDTO;
import com.coinpilot.dto.TransactionPatchDTO;
import com.coinpilot.dto.TransactionRequestDTO;
import com.coinpilot.dto.TransactionResponseDTO;
import com.coinpilot.exception.CurrencyMismatchException;
import com.coinpilot.exception.TransactionNotFoundException;
import com.coinpilot.exception.WalletNotFoundException;
import com.coinpilot.mapper.TransactionMapper;
import com.coinpilot.model.Transaction;
import com.coinpilot.model.TransactionType;
import com.coinpilot.model.Wallet;
import com.coinpilot.repository.TransactionRepository;
import com.coinpilot.repository.WalletRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        Wallet wallet = walletRepository.findById(transactionRequestDTO.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(transactionRequestDTO.getWalletId()));
        if (!wallet.getCurrency().equals(transactionRequestDTO.getCurrency())) {
            throw new CurrencyMismatchException(wallet.getCurrency(), transactionRequestDTO.getCurrency());
        }
        if (transactionRequestDTO.getType().equals(TransactionType.EXPENSE)) {
            wallet.setBalance(wallet.getBalance().subtract(transactionRequestDTO.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().add(transactionRequestDTO.getAmount()));
        }
        TransactionResponseDTO transactionResponseDTO = transactionMapper.transactionToResponseDTO(transactionRepository.save(
                transactionMapper.requestDTOtoTransaction(transactionRequestDTO, wallet)));
        walletRepository.save(wallet);
        return transactionResponseDTO;
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::transactionToResponseDTO)
                .toList();
    }

    public TransactionResponseDTO getTransactionById(Long id) {
        return transactionMapper.transactionToResponseDTO(transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id)));
    }

    @Transactional
    public TransactionResponseDTO updateTransactionById(Long id, TransactionRequestDTO transactionRequestDTO) {
        Wallet wallet = walletRepository.findById(transactionRequestDTO.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(transactionRequestDTO.getWalletId()));
        if (!wallet.getCurrency().equals(transactionRequestDTO.getCurrency())) {
            throw new CurrencyMismatchException(wallet.getCurrency(), transactionRequestDTO.getCurrency());
        }
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        if (transaction.getType().equals(TransactionType.EXPENSE)) {
            wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
        }
        Transaction updatedTransaction = transactionMapper.requestDTOtoTransaction(transactionRequestDTO, wallet);
        updatedTransaction.setId(id);
        if (updatedTransaction.getType().equals(TransactionType.EXPENSE)) {
            wallet.setBalance(wallet.getBalance().subtract(updatedTransaction.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().add(updatedTransaction.getAmount()));
        }
        walletRepository.save(wallet);
        return transactionMapper.transactionToResponseDTO(transactionRepository.save(updatedTransaction));
    }

    @Transactional
    public void deleteTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        Wallet wallet = walletRepository.findById(transaction.getWallet().getId())
                .orElseThrow(() -> new WalletNotFoundException(transaction.getWallet().getId()));
        if (transaction.getType().equals(TransactionType.EXPENSE)) {
            wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
        }
        walletRepository.save(wallet);
        transactionRepository.deleteById(id);
    }

    @Transactional
    public TransactionResponseDTO patchTransactionById(Long id, TransactionPatchDTO transactionPatchDTO) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        Wallet wallet = walletRepository.findById(transaction.getWallet().getId())
                .orElseThrow(() -> new WalletNotFoundException(transaction.getWallet().getId()));
        if (transaction.getType().equals(TransactionType.EXPENSE)) {
            wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
        }
        Wallet targetWallet = transactionPatchDTO.getWalletId()
                .filter(walletId -> !walletId.equals(transaction.getWallet().getId()))
                .map(walletId -> walletRepository.findById(walletId)
                        .orElseThrow(() -> new WalletNotFoundException(walletId)))
                .orElse(wallet);
        Transaction patchedTransaction = transactionMapper
                .transactionPatchDtoToTransaction(transactionPatchDTO, transaction, targetWallet);
        if (!targetWallet.getCurrency().equals(patchedTransaction.getCurrency())) {
            throw new CurrencyMismatchException(targetWallet.getCurrency(), patchedTransaction.getCurrency());
        }
        if (patchedTransaction.getType().equals(TransactionType.EXPENSE)) {
            targetWallet.setBalance(targetWallet.getBalance().subtract(patchedTransaction.getAmount()));
        } else {
            targetWallet.setBalance(targetWallet.getBalance().add(patchedTransaction.getAmount()));
        }
        walletRepository.save(wallet);
        if (!wallet.equals(targetWallet)) {
            walletRepository.save(targetWallet);
        }
        return transactionMapper.transactionToResponseDTO(transactionRepository.save(patchedTransaction));
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