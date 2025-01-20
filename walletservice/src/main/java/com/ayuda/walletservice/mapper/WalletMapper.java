package com.ayuda.walletservice.mapper;

import com.ayuda.walletservice.dto.CreateWalletDto;
import com.ayuda.walletservice.dto.WalletDto;
import com.ayuda.walletservice.entity.Wallet;
import com.ayuda.walletservice.enums.WalletStatus;
import com.ayuda.walletservice.utils.helpers.AccountNumberGenerator;

import java.math.BigDecimal;

public class WalletMapper {

    public static Wallet mapFromCreateWalletDto(CreateWalletDto createWalletDto) {
        Wallet wallet = new Wallet();
        wallet.setUserId(createWalletDto.getUserId());
        wallet.setWalletName(createWalletDto.getWalletName());
        wallet.setWalletType(createWalletDto.getWalletType());
        wallet.setCurrency(createWalletDto.getCurrency());
        wallet.setBalance(BigDecimal.ZERO); // Default balance is 0
        wallet.setStatus(WalletStatus.ACTIVE); // Default status is ACTIVE
        wallet.setDefault(createWalletDto.isDefault());
        wallet.setDescription(createWalletDto.getDescription());
        return wallet;
    }

    public static WalletDto mapToDto(Wallet wallet) {
        return new WalletDto(
                wallet.getId(),
                wallet.getUserId(),
                wallet.getWalletName(),
                wallet.getWalletType(),
                wallet.getCurrency(),
                wallet.getBalance(),
                wallet.getStatus(),
                wallet.isDefault(),
                wallet.getDescription(),
                wallet.getAccountNumber()
        );
    }
}
