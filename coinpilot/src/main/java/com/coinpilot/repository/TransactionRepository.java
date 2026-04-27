package com.coinpilot.repository;

import com.coinpilot.model.Transaction;
import com.coinpilot.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findByTypeAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end);
}