package com.coinpilot.repository;

import com.coinpilot.dto.CategorySumDTO;
import com.coinpilot.model.Transaction;
import com.coinpilot.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findByTypeAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(t.amount) " +
            "FROM Transaction t " +
            "WHERE t.type = :type " +
            "AND t.date >= :start " +
            "AND t.date <= :end")
    BigDecimal sumByTypeAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end);

    @Query("SELECT new com.coinpilot.dto.CategorySumDTO (t.category, SUM(t.amount)) " +
            "FROM Transaction t " +
            "WHERE t.type = :type " +
            "AND t.date >= :start " +
            "AND t.date <= :end " +
            "AND t.category IS NOT NULL " +
            "GROUP BY t.category")
    List<CategorySumDTO> sumByCategoriesAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end);
    List<Transaction> findByWalletId(Long walletId);
}