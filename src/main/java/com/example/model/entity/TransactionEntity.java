package com.example.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity(name = "Transactions")
public class TransactionEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "currency")
    private String currency;

    @Column(name = "amount")
    private int amount;

    @Column(name = "merchant_category_code")
    @JsonProperty("merchant_category_code")
    private String merchantCategoryCode;

    // constructors


    public TransactionEntity(Long id, String location, String currency, int amount, String merchantCategoryCode) {
        this.id = id;
        this.location = location;
        this.currency = currency;
        this.amount = amount;
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public TransactionEntity() {

    }

    // getters and setters

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
}
