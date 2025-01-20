package com.ayuda.transactionservice.mapper;

import com.ayuda.transactionservice.dto.*;
import com.ayuda.transactionservice.entity.Transaction;
import com.ayuda.transactionservice.enums.TransactionLedgerEntryType;
import com.ayuda.transactionservice.enums.TransactionStatus;

import java.math.BigDecimal;

public class TransactionMapper {

    // Convert CreateTransactionDTO to Transaction Entity
    public static Transaction mapFromCreateTransactionDTO(CreateTransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setAmount(dto.getAmount());
        transaction.setCurrency(dto.getCurrency());
        transaction.setLedgerEntryType(dto.getLedgerEntryType());
        transaction.setUserId(dto.getUserId());
        transaction.setSource(dto.getSource());
        transaction.setDestination(dto.getDestination());
        transaction.setWalletId(dto.getWalletId());
        transaction.setRecipientWalletId(dto.getRecipientWalletId());
        transaction.setCardId(dto.getCardId());
        transaction.setTransactionFee(dto.getTransactionFee());
        transaction.setReversal(dto.isReversal());
        transaction.setDescription(dto.getDescription());
        transaction.setStatus(dto.getStatus());
        return transaction;
    }

    // Convert Transaction Entity to TransactionDTO
    public static TransactionDto mapToDTO(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getReference(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getStatus(),
                transaction.getLedgerEntryType(),
                transaction.getUserId(),
                transaction.getSource(),
                transaction.getDestination(),
                transaction.getWalletId(),
                transaction.getRecipientWalletId(),
                transaction.getCardId(),
                transaction.getTransactionFee(),
                transaction.isReversal(),
                transaction.getDescription(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }

    public static Transaction mapIntoReversedTransaction(Transaction originalTransaction) {
        Transaction reversalTransaction = new Transaction();

        reversalTransaction.setReference(originalTransaction.getReference() + "-REVERSAL");
        reversalTransaction.setTransactionType(originalTransaction.getTransactionType());
        reversalTransaction.setAmount(originalTransaction.getAmount()); // No negation
        reversalTransaction.setCurrency(originalTransaction.getCurrency());
        reversalTransaction.setStatus(TransactionStatus.SUCCESS); // Mark as successful

        // Flip the Ledger Entry Type instead of negating the amount
        reversalTransaction.setLedgerEntryType(
                originalTransaction.getLedgerEntryType() == TransactionLedgerEntryType.DEBIT
                        ? TransactionLedgerEntryType.CREDIT
                        : TransactionLedgerEntryType.DEBIT
        );

        reversalTransaction.setUserId(originalTransaction.getUserId());
        reversalTransaction.setSource(originalTransaction.getSource());
        reversalTransaction.setDestination(originalTransaction.getDestination());
        reversalTransaction.setWalletId(originalTransaction.getWalletId());
        reversalTransaction.setRecipientWalletId(originalTransaction.getRecipientWalletId());
        reversalTransaction.setCardId(originalTransaction.getCardId());
        reversalTransaction.setTransactionFee(BigDecimal.ZERO); // No fee for reversals
        reversalTransaction.setReversal(true);
        reversalTransaction.setDescription("Reversal of transaction " + originalTransaction.getReference());

        return reversalTransaction;
    }

}
