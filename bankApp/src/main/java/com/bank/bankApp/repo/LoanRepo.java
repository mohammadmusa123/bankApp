package com.bank.bankApp.repo;

import com.bank.bankApp.model.Account;
import com.bank.bankApp.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepo extends JpaRepository<Loan,Integer> {
//    Account findByAccountId(int id);
    List<Loan> findByStartDate(LocalDate repaymentDate);
}
