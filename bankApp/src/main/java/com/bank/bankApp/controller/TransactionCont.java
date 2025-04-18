package com.bank.bankApp.controller;

import com.bank.bankApp.model.Transactions;
import com.bank.bankApp.service.EmailService;
import com.bank.bankApp.service.TransactionService;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TransactionCont {

    @Autowired
    TransactionService service;

//    @Autowired
//    private EmailService emailService;

    @PostMapping("/transfer")
    public String transfer(@RequestParam int fromAccountId, @RequestParam int toAccountId, @RequestParam BigDecimal amount)
    {
//        emailService.sendMail("");
        return service.transfer(fromAccountId,toAccountId,amount);
    }

    @GetMapping("/filterStatement/{fromDate}/{toDate}")
    public List<Transactions> filterByDate(@PathVariable() @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                           @PathVariable() @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate)
    {
        LocalDateTime fromDate1=fromDate.atStartOfDay();
        LocalDateTime toDate1=toDate.atTime(23,59,59);
        return service.filterByDate(fromDate1, toDate1);
    }
}
