package com.ayuda.walletservice.service;

import com.ayuda.walletservice.dto.CreateWalletDto;
import com.ayuda.walletservice.dto.WalletDto;
import com.ayuda.walletservice.enums.WalletStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IWalletService {

    /**
     *
     * @param createWalletDto
     * @return
     */
    WalletDto createWallet(CreateWalletDto createWalletDto);

    /**
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<WalletDto> getUserWallets(String userId, Pageable pageable);

    /**
     *
     * @param walletId
     * @return
     */
    WalletDto getWalletById(String walletId);

    /**
     * 
     * @param walletId
     * @return
     */
    BigDecimal getWalletBalance(String walletId);

    /**
     *
     * @param walletId
     * @param status
     * @return
     */
    WalletDto changeWalletStatus(String walletId, WalletStatus status);

    /**
     *
     * @param walletId
     * @param amount
     * @param isCredit
     * @return
     */
    WalletDto updateWalletBalance(String walletId, BigDecimal amount, boolean isCredit);
}
