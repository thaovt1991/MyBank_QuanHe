package com.codegym.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private long amount;
    private boolean isDelete = false ;
    private LocalDateTime created_at = LocalDateTime.now();



    public Deposit(){}

    public Deposit(Long id, Customer customer, long amount) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
    }

    public Deposit(Long id, Customer customer, long amount, boolean isDelete, LocalDateTime created_at) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.isDelete = isDelete;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime datetime) {
        this.created_at = datetime;
    }
}
