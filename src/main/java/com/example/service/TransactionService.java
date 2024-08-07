package com.example.service;


import com.example.model.entity.TransactionEntity;
import com.example.repository.TransactionRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transaction;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    @Transactional
    public void save(TransactionEntity transaction) {
        transactionRepository.persist(transaction);
    }

    @Transactional
    public Optional<TransactionEntity> updateTransaction( TransactionEntity transaction) {
        return Optional.ofNullable(transactionRepository.updateTransaction(transaction));
    }

    public Optional<TransactionEntity> findById(Long id) {
        return transactionRepository.findByIdOptional(id);
    }

    @Transactional
    public void delete (Long id) {
        TransactionEntity transaction =transactionRepository.findById(id);
        if (transaction != null) {

            transactionRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException ("Transaction doesn't exists!");
        }

    }

    public List<TransactionEntity> findAll(int page, int size) {
        return transactionRepository.findAll().page(Page.of(page, size)).list();
    }
}
