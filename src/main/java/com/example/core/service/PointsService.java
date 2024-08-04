package com.example.core.service;

import com.example.core.domain.Transaction;
import com.example.core.entity.PointsHistory;
import com.example.core.entity.TransactionEntity;
import com.example.core.entity.User;
import com.example.core.mapper.TransactionMapper;
import com.example.core.repository.PointsHistoryRepository;
import com.example.core.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;


@ApplicationScoped
public class PointsService {

    @Inject
    UserRepository userRepository;

    @Inject
    PointsHistoryRepository pointsHistoryRepository;

    @Inject
    TransactionMapper transactionMapper;

    // private static final AtomicLong idGenerator = new AtomicLong(1);

    @Transactional
    public void processTransaction(Transaction transaction) {
        // Convert Transaction to TransactionEntity using the mapper
        TransactionEntity transactionEntity = transactionMapper.toEntity(transaction);

        // Process the transaction
        User user = userRepository.findById(transactionEntity.getUserId());
        if (user != null) {
            user.points += transactionEntity.getQuantity();
            user.persist();

            PointsHistory pointsHistory = new PointsHistory();
            // pointsHistory.id = idGenerator.getAndIncrement();
            pointsHistory.user = user;
            pointsHistory.points = transactionEntity.getQuantity();
            pointsHistory.newTotal = user.points;
            pointsHistory.transactionId = transactionEntity.getId();
            pointsHistory.persist();
        }
    }


    public void addPoints(Transaction transaction) {
        // Ensure findById returns an Optional<User> and call orElseThrow on it
        User user = userRepository.findByIdOptional(transaction.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        int points = calculatePoints(transaction);
        user.points += points;
        userRepository.persist(user);
    }

    private int calculatePoints(Transaction transaction) {
        if (transaction.getCurrency().equals("EUR")) {
            return transaction.getQuantity() * 2;
        }
        return transaction.getQuantity();
    }
}