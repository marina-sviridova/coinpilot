package com.coinpilot.service;

import com.coinpilot.dto.TransactionPatchDTO;
import com.coinpilot.dto.TransactionRequestDTO;
import com.coinpilot.dto.TransactionResponseDTO;
import com.coinpilot.mapper.TransactionMapper;
import com.coinpilot.model.Transaction;
import com.coinpilot.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        return transactionMapper.transactionToResponseDTO(transactionRepository
                .save(transactionMapper.requestDTOtoTransaction(transactionRequestDTO)));
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
        if (transactionRepository.existsById(id)) {
            Transaction updatedTransaction = transactionMapper.requestDTOtoTransaction(transactionRequestDTO);
            updatedTransaction.setId(id);
            return Optional.of(transactionMapper.transactionToResponseDTO(transactionRepository.save(updatedTransaction)));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteTransactionById(Long id) {
        boolean isTransactionExists = transactionRepository.existsById(id);
        if(isTransactionExists) {
            transactionRepository.deleteById(id);
        }
        return isTransactionExists;
    }

    public Optional<TransactionResponseDTO> patchTransactionById(Long id, TransactionPatchDTO transactionPatchDTO) {
        return transactionRepository.findById(id)
                .map(transaction -> transactionMapper.transactionPatchDtoToTransaction(transactionPatchDTO, transaction))
                .map(transaction -> transactionRepository.save(transaction))
                .map(transaction -> transactionMapper.transactionToResponseDTO(transaction));
    }
}