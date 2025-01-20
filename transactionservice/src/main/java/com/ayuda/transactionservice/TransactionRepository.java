package com.ayuda.transactionservice;

import com.ayuda.transactionservice.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Page<Transaction> findByUserId(String userId, Pageable pageable);

    Page<Transaction> findByWalletId(String walletId, Pageable pageable);

    Page<Transaction> findByCardId(String cardId, Pageable pageable);
}