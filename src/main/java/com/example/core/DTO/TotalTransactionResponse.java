package com.example.core.DTO;

import com.example.core.domain.Transaction;

import java.util.List;

public class TotalTransactionResponse {

    private int totalBalance;
    private List<Transaction> transactions;

    // getters and setters

    public int getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
