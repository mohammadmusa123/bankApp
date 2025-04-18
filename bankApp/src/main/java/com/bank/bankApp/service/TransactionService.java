package com.bank.bankApp.service;

import com.bank.bankApp.model.Account;
import com.bank.bankApp.model.Transactions;
import com.bank.bankApp.model.Users;
import com.bank.bankApp.repo.AccRepo;
import com.bank.bankApp.repo.TransRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    AccRepo accRepo;

    @Autowired
    TransRepo transRepo;

    @Autowired
    EmailService emailService;

    @Transactional
    public String transfer(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Source and Destination account should not be same");
        }

        Account fromAcc = accRepo.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source Account not found"));
        Account toAcc = accRepo.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Destination Account not found"));

        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        Transactions transactions = new Transactions();
        fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
        transactions.setType("credit");
        toAcc.setBalance(toAcc.getBalance().add(amount));

        transactions.setDebitAccount(fromAcc);
        transactions.setCreditAccount(toAcc);

        transactions.setFromAccountId(fromAccountId);
        transactions.setToAccountId(toAccountId);
        transactions.setAmount(amount);
        transactions.setTimestamp(LocalDateTime.now());

        Users toUsers = toAcc.getUsers();
        String toMail = toUsers.getMail();
        String body = "Dear " + toUsers.getUsername() + ",\n\n      Transaction successful.Your Account credited " + amount + "Rs/-\n\nThank you";
        emailService.sendMail(toMail, "CREDIT Transaction", body);
        Users fromUser = fromAcc.getUsers();
        String fromMail=fromUser.getMail();
        String body1="Dear "+fromUser.getUsername()+",\n\n     Transaction Successful.Your Account debited "+amount+"Rs/-\n\nThank you";
        emailService.sendMail(fromMail,"DEBIT Transaction",body1);
        transRepo.save(transactions);
        return "Transaction Successful";
    }

    public List<Transactions> filterByDate(LocalDateTime fromDate, LocalDateTime toDate) {
//        Long id1= (Long) id;
//        List<Transactions> transactions=transRepo.findByDebitAccountId(id);
//        return transactions.stream().filter(transactions1 -> transactions1.getTimestamp().isAfter(fromDate) &&
//                transactions1.getTimestamp().isBefore(toDate)).collect(Collectors.toList());
        return transRepo.findByTimestampBetween(fromDate,toDate);
    }
}
