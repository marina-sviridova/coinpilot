package com.coinpilot.mapper;

import com.coinpilot.dto.TransactionPatchDTO;
import com.coinpilot.dto.TransactionRequestDTO;
import com.coinpilot.dto.TransactionResponseDTO;
import com.coinpilot.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction requestDTOtoTransaction(TransactionRequestDTO requestDTO) {
        return Transaction.builder()
                .type(requestDTO.getType())
                .amount(requestDTO.getAmount())
                .currency(requestDTO.getCurrency())
                .date(requestDTO.getDate())
                .category(requestDTO.getCategory())
                .description(requestDTO.getDescription())
                .build();
    }

    public TransactionResponseDTO transactionToResponseDTO(Transaction transaction) {
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .date(transaction.getDate())
                .category(transaction.getCategory())
                .description(transaction.getDescription())
                .build();
    }

    public Transaction transactionPatchDtoToTransaction(TransactionPatchDTO transactionPatchDTO, Transaction transaction) {

        transactionPatchDTO.getType()
                .ifPresent(transactionType -> transaction.setType(transactionType));
        transactionPatchDTO.getAmount()
                .ifPresent(amount -> transaction.setAmount(amount));
        transactionPatchDTO.getCurrency()
                .ifPresent(currency -> transaction.setCurrency(currency));
        transactionPatchDTO.getDate()
                .ifPresent(date -> transaction.setDate(date));
        transactionPatchDTO.getCategory()
                .ifPresent(category -> transaction.setCategory(category));
        transactionPatchDTO.getDescription()
                .ifPresent(description -> transaction.setDescription(description));
        return transaction;
    }
}