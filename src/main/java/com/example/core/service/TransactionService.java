package com.example.core.service;


import com.example.core.DTO.TotalTransactionResponse;
import com.example.core.domain.Transaction;
import com.example.core.entity.TransactionEntity;
import com.example.core.mapper.TransactionMapper;
import com.example.core.repository.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped  // this means that we will inject dependencies from here to use in controller for example
public class TransactionService {
    @Inject
    TransactionRepository transactionRepository;

    @Transactional
    public void save(Transaction transaction) {
        TransactionEntity entity = TransactionMapper.INSTANCE.toEntity(transaction);
        transactionRepository.persist(entity);
    }


    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findByIdOptional(id)
                .map(TransactionMapper.INSTANCE::toDomain);
    }

    public Optional<TotalTransactionResponse> getTotalTransactionResponse() {
        List<TransactionEntity> entities = transactionRepository.findAll().list();
        if (entities.isEmpty()) {
            return Optional.empty();
        }

        List<Transaction> transactions = entities.stream()
                .map(TransactionMapper.INSTANCE::toDomain)
                .collect(Collectors.toList());

        int totalBalance = transactions.stream().mapToInt(Transaction::getQuantity).sum();

        TotalTransactionResponse response = new TotalTransactionResponse();
        response.setTotalBalance(totalBalance);
        response.setTransactions(transactions);

        return Optional.of(response);

    }
}

