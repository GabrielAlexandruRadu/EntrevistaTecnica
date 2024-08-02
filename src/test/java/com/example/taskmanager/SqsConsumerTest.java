package com.example.taskmanager;


import com.example.core.domain.Transaction;
import com.example.core.entity.User;
import com.example.core.repository.UserRepository;
import com.example.core.service.PointsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SqsConsumerTest {

    @InjectMocks
    PointsService pointsService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Ensure a user is in the database for testing
        User user = new User();
        user.id = 1L;
        user.username = "testuser";
        user.password = "testpassword";
        user.points = 0;

        // Configure the userRepository mock
        when(userRepository.findByIdOptional(1L)).thenReturn(Optional.of(user));
    }

    @Test
    public void GivenTransactionWhenConsumedThenPointsUpdated() {
        Transaction transaction = new Transaction();
        transaction.setUserId(1L); // Assuming the user ID is 1
        transaction.setQuantity(100);
        transaction.setCurrency("EUR");

        pointsService.addPoints(transaction);

        Optional<User> user = userRepository.findByIdOptional(1L);
        assertEquals(200, user.get().getPoints()); // 100 * 2 for EUR
    }
}
