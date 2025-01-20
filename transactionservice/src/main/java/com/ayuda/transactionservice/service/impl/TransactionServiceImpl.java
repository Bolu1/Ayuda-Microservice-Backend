package com.ayuda.transactionservice.service.impl;

import com.ayuda.transactionservice.repository.TransactionRepository;
import com.ayuda.transactionservice.dto.CreateTransactionDto;
import com.ayuda.transactionservice.dto.TransactionDto;
import com.ayuda.transactionservice.entity.Transaction;
import com.ayuda.transactionservice.enums.TransactionStatus;
import com.ayuda.transactionservice.mapper.TransactionMapper;
import com.ayuda.transactionservice.service.ITransactionService;
import com.ayuda.transactionservice.utils.helpers.ReferenceGenerator;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final ReferenceGenerator referenceGenerator;
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    /**
     * @param createTransactionDto
     * @return
     */
    @Override
    public TransactionDto createTransaction(CreateTransactionDto createTransactionDto) {
        Transaction transaction = TransactionMapper.mapFromCreateTransactionDTO(createTransactionDto);
        transaction.setReference(referenceGenerator.generateTransactionReference()); // Generate unique reference
        transaction = transactionRepository.save(transaction);
        return TransactionMapper.mapToDTO(transaction);
    }

    /**
     * @param transactionId
     * @return
     */
    @Override
    public TransactionDto getTransactionById(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new BadRequestException("Transaction not found"));
        return TransactionMapper.mapToDTO(transaction);
    }

    /**
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public Page<TransactionDto> getUserTransactions(String userId, Pageable pageable) {
        return transactionRepository.findByUserId(userId, pageable)
                .map(TransactionMapper::mapToDTO);
    }

    /**
     * @param walletId
     * @param pageable
     * @return
     */
    @Override
    public Page<TransactionDto> getWalletTransactions(String walletId, Pageable pageable) {
        return transactionRepository.findByWalletId(walletId, pageable)
                .map(TransactionMapper::mapToDTO);
    }

    /**
     * @param cardId
     * @param pageable
     * @return
     */
    @Override
    public Page<TransactionDto> getCardTransactions(String cardId, Pageable pageable) {
        return transactionRepository.findByCardId(cardId, pageable)
                .map(TransactionMapper::mapToDTO);
    }

    /**
     * @param transactionId
     * @return
     */
    @Override
    public TransactionDto reverseTransaction(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Ensure only successful transactions can be reversed
        if (!transaction.getStatus().equals(TransactionStatus.SUCCESS)) {
            throw new RuntimeException("Only successful transactions can be reversed");
        }

        // Generate the reversed transaction
        Transaction reversalTransaction = TransactionMapper.mapIntoReversedTransaction(transaction);

        // Save and return the reversal transaction
        transactionRepository.save(reversalTransaction);
        return TransactionMapper.mapToDTO(reversalTransaction);
    }
}
