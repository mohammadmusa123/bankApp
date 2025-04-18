package com.bank.bankApp.controller;

import com.bank.bankApp.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class LoanCont {

    @Autowired
    LoanService loanService;

    @GetMapping("/check")
    public ResponseEntity<String> checkEligibility(@RequestParam int accountId, @RequestParam BigDecimal amount)
    {
        return loanService.checkEligibility(accountId,amount);
    }

    @PostMapping("/applyloan")
    public ResponseEntity<?> applyLoan(@RequestParam int accountId, @RequestParam String loanType,@RequestParam BigDecimal amount,@RequestParam int noOfMonths)
    {
        return loanService.applyLoan(accountId,loanType,amount,noOfMonths);
    }
}
