package com.ayuda.walletservice.service.impl;

import com.ayuda.walletservice.dto.CreateWalletDto;
import com.ayuda.walletservice.dto.WalletDto;
import com.ayuda.walletservice.entity.Wallet;
import com.ayuda.walletservice.enums.WalletStatus;
import com.ayuda.walletservice.mapper.WalletMapper;
import com.ayuda.walletservice.repository.WalletRepository;
import com.ayuda.walletservice.service.IWalletService;
import com.ayuda.walletservice.utils.helpers.AccountNumberGenerator;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private final WalletRepository walletRepository;
    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);


    /**
     * @param createWalletDto
     * @return
     */
    @Override
    public WalletDto createWallet(CreateWalletDto createWalletDto) {
        Wallet wallet = WalletMapper.mapFromCreateWalletDto(createWalletDto);
        wallet.setAccountNumber(AccountNumberGenerator.generateAccountNumber()); // Generate unique account number
        wallet = walletRepository.save(wallet);
        return WalletMapper.mapToDto(wallet);
    }

    /**
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public Page<WalletDto> getUserWallets(String userId, Pageable pageable) {
        return walletRepository.findByUserId(userId, pageable)
                .map(WalletMapper::mapToDto);
    }

    /**
     * @param walletId
     * @return
     */
    @Override
    public WalletDto getWalletById(String walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new BadRequestException("Wallet not found"));
        return WalletMapper.mapToDto(wallet);

    }

    /**
     * @param walletId
     * @return
     */
    @Override
    public BigDecimal getWalletBalance(String walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new BadRequestException("Wallet not found"));
        return wallet.getBalance();
    }

    /**
     * @param walletId
     * @param status
     * @return
     */
    @Override
    public WalletDto changeWalletStatus(String walletId, WalletStatus status) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new BadRequestException("Wallet not found"));
        wallet.setStatus(status);
        wallet = walletRepository.save(wallet);
        return WalletMapper.mapToDto(wallet);
    }

    @Override
    public WalletDto updateWalletBalance(String walletId, BigDecimal amount, boolean isCredit) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new BadRequestException("Wallet not found"));

        if (isCredit) {
            wallet.setBalance(wallet.getBalance().add(amount));
        } else {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new BadRequestException("Insufficient balance");
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
        }

        wallet = walletRepository.save(wallet);
        return WalletMapper.mapToDto(wallet);
    }
}
