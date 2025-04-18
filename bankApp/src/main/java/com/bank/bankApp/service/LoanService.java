package com.bank.bankApp.service;

import com.bank.bankApp.model.Account;
import com.bank.bankApp.model.Loan;
import com.bank.bankApp.repo.AccRepo;
import com.bank.bankApp.repo.LoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    AccRepo accRepo;
    @Autowired
    LoanRepo loanRepo;

    public ResponseEntity<String> checkEligibility(int accountId, BigDecimal amount) {
        Optional<Account> account = accRepo.findById(accountId);
        if (account.isEmpty()) {
            return new ResponseEntity<>("Account not found",HttpStatus.BAD_REQUEST);
        }
        Account account1=account.get();
        if (account1.getBalance().compareTo(amount.multiply(BigDecimal.valueOf(0.7))) > 0)
        {
            return new ResponseEntity<>("You are Eligible for Loan amount of "
                    +amount+" with the interest of 12% and the total amount with interest is "
                    +amount.multiply(BigDecimal.valueOf(12)).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP).add(amount) ,HttpStatus.OK);
        }
        return new ResponseEntity<>("You are not Eligible for Loan",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> applyLoan(int accountId,String loanType,BigDecimal amount,int noOfMonths) {
        Optional<Account> account=accRepo.findById(accountId);
        Account account1=account.get();
        Loan  loan=new Loan();
        if (account1.getBalance().compareTo(amount.multiply(BigDecimal.valueOf(0.7))) > 0)
        {
            loan.setAccount(account1);
            loan.setLoanType(loanType);
            loan.setAmount(amount);
            loan.setNoOfMonths(noOfMonths);
            loan.setLoanStatus("Pending");
            loan.setInterestRate(BigDecimal.valueOf(12));
            loan.setStartDate(LocalDate.now().plusDays(5));
            loan.setEndDate(loan.getStartDate().plusMonths(loan.getNoOfMonths()));
            loan.setTotalPayableAmount(loan.getAmount().add(loan.getAmount()
                    .multiply(loan.getInterestRate().divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP))));
            BigDecimal totalEMI=loan.getTotalPayableAmount().divide(BigDecimal.valueOf(noOfMonths),2, RoundingMode.HALF_UP);
            loan.setEmiAmount(totalEMI);
            List<LocalDate> dates=new ArrayList<>();
            for (int i=0;i< noOfMonths;i++)
                dates.add(loan.getStartDate().plusMonths(i));
            loan.setRepaymentDate(dates);
            loanRepo.save(loan);
            return new ResponseEntity<>(loan,HttpStatus.OK);

        }
        return new ResponseEntity<>("Please check and Enter valid details",HttpStatus.BAD_REQUEST);

    }

    @Scheduled(cron = "0 40 12 * * ?")
    public ResponseEntity<?> repayment()
    {
        List<Loan> allLoans= loanRepo.findByStartDate(LocalDate.now());
        for (Loan loan:allLoans)
        {
            Optional<Account> account=accRepo.findById(loan.getAccount().getId());
            Account account1=account.get();
            if (account1 != null && account1.getBalance().compareTo(loan.getEmiAmount()) >= 0 ) {
                account1.setBalance(account1.getBalance().subtract(loan.getEmiAmount()));
                accRepo.save(account1);
                loan.setRepaymentDate(Collections.singletonList(loan.getRepaymentDate().removeFirst()));
                loan.setStartDate(LocalDate.now().plusMonths(1));
                loanRepo.save(loan);
                System.out.println("EMI amount debited successfully of Loan ID "+loan.getId());
                return new ResponseEntity<>("EMI Amount of " + loan.getEmiAmount() + " debited successfully from " + account1.getId(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Insufficient Fund!!",HttpStatus.BAD_REQUEST);
    }
}
