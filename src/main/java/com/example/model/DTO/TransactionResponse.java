package com.example.model.DTO;

import com.example.model.entity.TransactionEntity;

public class TransactionResponse {
    private Long id;
    private String location;
    private String currency;
    private int amount;
    private String merchantCategoryCode;
    private String saludo;

    public TransactionResponse(TransactionEntity transaction) {
        this.id = transaction.getId();
        this.location = transaction.getLocation();
        this.currency = transaction.getCurrency();
        this.amount = transaction.getAmount();
        this.merchantCategoryCode = transaction.getMerchantCategoryCode();
        this.saludo = "hola";
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getSaludo() {
        return saludo;
    }

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }
}