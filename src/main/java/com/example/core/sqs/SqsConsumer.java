package com.example.core.sqs;


import com.example.core.domain.Transaction;
import com.example.core.service.PointsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class SqsConsumer {

    @Inject
    PointsService pointsService;

    @Incoming("transactions")
    @Blocking
    public void consume(String message) {
        Transaction transaction = parseTransaction(message);
        pointsService.addPoints(transaction);
    }

    private Transaction parseTransaction(String message) {
        try {
            return new ObjectMapper().readValue(message, Transaction.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse SQS message", e);
        }
    }
}
