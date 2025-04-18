package com.bank.bankApp.repo;

import com.bank.bankApp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccRepo extends JpaRepository<Account,Integer> {
    List<Account> findByUsersId(int id);
}
