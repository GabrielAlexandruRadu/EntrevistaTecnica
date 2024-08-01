package com.example.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import javax.lang.model.element.Name;


@Entity(name = "Transaction")  // Esto quiere decir que esta clase se tiene que mapear en la base de datos por hibernate
public class TransactionEntity {

    @Id

    private Long id;

    @Column(name = "location")
    private String location;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "currency")
    private String currency;

    @Column(name = "merchant_category_code")
    @JsonProperty("merchant_category_code")
    private String merchantCategoryCode;

    // Default constructor
    public TransactionEntity() {}

    // Parameterized constructor
    public TransactionEntity(Long id, String location, int quantity, String currency, String merchantCategoryCode) {
        this.id = id;
        this.location = location;
        this.quantity = quantity;
        this.currency = currency;
        this.merchantCategoryCode = merchantCategoryCode;
    }



    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }




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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
