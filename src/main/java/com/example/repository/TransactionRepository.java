package com.example.repository;

import com.example.model.entity.TransactionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;


@ApplicationScoped
public class TransactionRepository  implements PanacheRepository<TransactionEntity> {

    public TransactionEntity  updateTransaction (TransactionEntity transaction) {
        Long id =transaction.getId();

        Optional<TransactionEntity>  existingTransaction = this.findByIdOptional(id);

        if (existingTransaction.isPresent()) {
            TransactionEntity updatedTransaction = existingTransaction.get();
            updatedTransaction.setLocation(transaction.getLocation());
            updatedTransaction.setLocation(transaction.getLocation());
            updatedTransaction.setCurrency(transaction.getCurrency());
            updatedTransaction.setAmount(transaction.getAmount());
            updatedTransaction.setMerchantCategoryCode(transaction.getMerchantCategoryCode());
            persistAndFlush(updatedTransaction);
            return updatedTransaction;
        } else {
            throw new IllegalArgumentException("Transaction is not present.");
        }
    }
}
