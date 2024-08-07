package com.example.service;


import com.example.model.entity.TransactionEntity;
import com.example.repository.TransactionRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionEntity transaction;

    @InjectMocks
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenTransactionWhenDeletedThenExpectNoContent () {

        //given
        when(transactionRepository.findById(any())).thenReturn(transaction);

        //when
        transactionService.delete(1L);

        //then
        verify(transactionRepository,times(1)).findById(any());
        verify(transactionRepository,times(1)).deleteById(any());

        verifyNoMoreInteractions(transactionRepository);


    }

    @Test
    void givenTransactionWhenDeletedThenExpectIllegalArgumentException () {

        //give
        when(transactionRepository.findById(any())).thenReturn(null);
        //when//then
        assertThrows( IllegalArgumentException.class,() -> transactionService.delete(1L));

    }
}
