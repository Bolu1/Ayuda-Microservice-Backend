package com.ayuda.walletservice.repository;

import com.ayuda.walletservice.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, String> {
    Optional<Wallet> findByAccountNumber(String accountNumber);
    Page<Wallet> findByUserId(String userId, Pageable pageable);
}
