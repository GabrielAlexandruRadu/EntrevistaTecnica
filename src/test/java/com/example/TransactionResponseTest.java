package com.example;


import com.example.model.DTO.TransactionResponse;
import com.example.model.entity.TransactionEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TransactionResponseTest {

    @Test
    public void givenTransactionEntityWhenCreatingTransactionResponseThenResponseIsCorrect () {
        // Arrange : crea el objeto entity
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1L);
        transaction.setLocation("New York");
        transaction.setCurrency("USD");
        transaction.setAmount(100);
        transaction.setMerchantCategoryCode("123code");

        // Act : crea el response a partir del entity
        TransactionResponse transactionResponse = new TransactionResponse(transaction);

        // Assert : comprobamos que tiene los datos esperados

        assertEquals(1L, transactionResponse.getId());
        assertEquals("New York", transactionResponse.getLocation());
        assertEquals("USD", transactionResponse.getCurrency());
        assertEquals(100, transactionResponse.getAmount());
        assertEquals("123code", transactionResponse.getMerchantCategoryCode());
        assertEquals("hola", transactionResponse.getSaludo());

    }
}
