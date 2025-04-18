package com.bank.bankApp.service;

import com.bank.bankApp.controller.AccountCont;
import com.bank.bankApp.model.Account;
import com.bank.bankApp.model.Transactions;
import com.bank.bankApp.model.UserPrinciple;
import com.bank.bankApp.model.Users;
import com.bank.bankApp.repo.AccRepo;
import com.bank.bankApp.repo.TransRepo;
import com.bank.bankApp.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class AccService {
    private static final Logger log = LoggerFactory.getLogger(AccService.class);
    @Autowired
    AccRepo accRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserPrinciple principle;

    @Autowired
    TransRepo transRepo;

    public String addAcc(String username,String password,Account account) {
        log.info("addAcc method in service : {}",account);
        Users users1=userRepo.findByUsername(username);
        log.info("user info: {}",users1);
        if (users1!=null)
        {
            log.info("add method service if block");
            account.setUsers(users1);
//            System.out.println(users1.getUsername());
            accRepo.save(account);
//            System.out.println(account.getBalance());
            return "Account added";
        }
        log.info("add method service outside if block");
        return "Please login User";
    }

    public ResponseEntity<Account> getAcc(int id) {
        log.info("getAcc service method with id {}: ",id);
        List<Transactions> debitTransactions= transRepo.findByDebitAccountId(id);
//        List<Transactions> creditTransactions= transRepo.findByCreditAccount(id);
        Account account=accRepo.findById(id).orElseThrow();
        account.setDebitTransactions(debitTransactions);
//        account.setCreditTransactions(creditTransactions);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


//    public List<Transactions> filterByDate(LocalDate fromDate, LocalDate toDate) {
//        List<Transactions> transactions=transRepo.findByDate(fromDate,toDate);
//        LocalDateTime start = LocalDateTime.of(fromDate, LocalTime time1);
//        LocalDateTime end = LocalDateTime.of(toDate, LocalTime time);
//        return transactions.stream().filter(transactions1 -> transactions1.getTimestamp().toLocalDate().isAfter(fromDate) &&
//                transactions1.getTimestamp().toLocalDate().isBefore(toDate)).collect(Collectors.toList());
//        return transactions;
//    }
}
