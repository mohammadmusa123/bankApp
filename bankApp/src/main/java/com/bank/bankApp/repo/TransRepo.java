package com.bank.bankApp.repo;

import com.bank.bankApp.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransRepo extends JpaRepository<Transactions,Integer> {
    List<Transactions> findByDebitAccountId(int id);

//    @Query("SELECT t from Transaction t where (t.fromAccountId=:id or t.toAccountId=:id) AND (t.timestamp between :fromDate AND :toDate)")
    List<Transactions> findByTimestampBetween(LocalDateTime fromDate, LocalDateTime toDate);
//
//    List<Transactions> findByCreditAccount(int id);
}
