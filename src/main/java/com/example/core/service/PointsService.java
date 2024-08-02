package com.example.core.service;

import com.example.core.domain.Transaction;
import com.example.core.entity.User;
import com.example.core.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class PointsService {
    @Inject
    UserRepository userRepository;

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