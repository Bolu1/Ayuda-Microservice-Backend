package com.ayuda.transactionservice.service;

import com.ayuda.transactionservice.dto.CreateTransactionDto;
import com.ayuda.transactionservice.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITransactionService {

    /**
     * @param createTransactionDto
     * @return
     */
    TransactionDto createTransaction(CreateTransactionDto createTransactionDto);

    /**
     * @param transactionId
     * @return
     */
    TransactionDto getTransactionById(String transactionId);

    /**
     * @param userId
     * @param pageable
     * @return
     */
    Page<TransactionDto> getUserTransactions(String userId, Pageable pageable);

    /**
     * @param walletId
     * @param pageable
     * @return
     */
    Page<TransactionDto> getWalletTransactions(String walletId, Pageable pageable);

    /**
     * @param cardId
     * @param pageable
     * @return
     */
    Page<TransactionDto> getCardTransactions(String cardId, Pageable pageable);


    /**
     * @param transactionId
     * @return
     */
    TransactionDto reverseTransaction(String transactionId);
}
