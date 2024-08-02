package com.example.core.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {
    @Column(unique = true)
    public String username;

    public String password;

    public int points;
}
