package com.example.core.repository;

import com.example.core.entity.TransactionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionRepository  implements PanacheRepository<TransactionEntity> {

}
