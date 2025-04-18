package com.bank.bankApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal balance;
    private String type;

    @ManyToOne()
    @JoinColumn(name = "users_id")
    @JsonBackReference("users-accounts")
    private Users users;

    @OneToMany(mappedBy = "debitAccount",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference("debitAccount-debitTransactions")
    private List<Transactions> debitTransactions;

    @OneToMany(mappedBy = "creditAccount",fetch = FetchType.EAGER)
    @JsonManagedReference("creditAccount-creditTransactions")
    private List<Transactions> creditTransactions;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Loan> loans;

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Transactions> getDebitTransactions() {
        return debitTransactions;
    }

    public void setDebitTransactions(List<Transactions> debitTransactions) {
        this.debitTransactions = debitTransactions;
    }

    public List<Transactions> getCreditTransactions() {
        return creditTransactions;
    }

    public void setCreditTransactions(List<Transactions> creditTransactions) {
        this.creditTransactions = creditTransactions;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
