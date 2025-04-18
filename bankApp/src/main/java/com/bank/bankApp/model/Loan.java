package com.bank.bankApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String loanType;
    private BigDecimal amount;
    private String loanStatus;
    private BigDecimal interestRate;
    private int noOfMonths;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPayableAmount;
    private BigDecimal emiAmount;
    private List<LocalDate> repaymentDate;

//    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "users_id")
//    @JsonBackReference
//    private Users users;

    @OneToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoOfMonths() {
        return noOfMonths;
    }

    public void setNoOfMonths(int noOfMonths) {
        this.noOfMonths = noOfMonths;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(BigDecimal totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public BigDecimal getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(BigDecimal emiAmount) {
        this.emiAmount = emiAmount;
    }

    public List<LocalDate> getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(List<LocalDate> repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
