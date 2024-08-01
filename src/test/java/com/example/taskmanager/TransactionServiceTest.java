package com.example.taskmanager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.core.DTO.TotalTransactionResponse;
import com.example.core.domain.Transaction;
import com.example.core.entity.TransactionEntity;
import com.example.core.repository.TransactionRepository;
import com.example.core.service.TransactionService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void givenTransactionsWhenGetTotalTransactionResponseThenExpectCorrectResponse() {

        TransactionEntity entity1 = new TransactionEntity(1L, "New York", 50, "USD", "1234");
        TransactionEntity entity2 = new TransactionEntity(2L, "Los Angeles", 100, "USD", "5678");

        // Create a mock PanacheQuery
        PanacheQuery<TransactionEntity> mockQuery = mock(PanacheQuery.class);

        // Configure the mock PanacheQuery to return the list of entities
        when(mockQuery.list()).thenReturn(List.of(entity1, entity2));

        // Configure the transactionRepository to return the mock PanacheQuery
        when(transactionRepository.findAll()).thenReturn(mockQuery);

        // Call the service method
        Optional<TotalTransactionResponse> response = transactionService.getTotalTransactionResponse();


        assertTrue(response.isPresent());
        assertEquals(150, response.get().getTotalBalance());
        List<Transaction> transactions = response.get().getTransactions();
        assertEquals(2, transactions.size());

        Transaction transaction1 = transactions.get(0);
        assertEquals(1, transaction1.getId());
        assertEquals("New York", transaction1.getLocation());
        assertEquals(50, transaction1.getQuantity());
        assertEquals("USD", transaction1.getCurrency());
        assertEquals("1234", transaction1.getMerchantCategoryCode());

        Transaction transaction2 = transactions.get(1);
        assertEquals(2, transaction2.getId());
        assertEquals("Los Angeles", transaction2.getLocation());
        assertEquals(100, transaction2.getQuantity());
        assertEquals("USD", transaction2.getCurrency());
        assertEquals("5678", transaction2.getMerchantCategoryCode());


    }
}
