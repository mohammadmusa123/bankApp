package com.bank.bankApp.controller;

import com.bank.bankApp.model.Account;
import com.bank.bankApp.service.AccService;
import com.bank.bankApp.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountCont {
    private static final Logger log = LoggerFactory.getLogger(AccountCont.class);
    @Autowired
    AccService service;

    @Autowired
    JwtService jwtService;

    @PostMapping("/add")
//    @PreAuthorize("isAuthenticated()")
    public String addAcc(@RequestParam String username,@RequestParam String password, @RequestBody Account account)
    {
        log.info("add endpoint controller : {}",account);
        return service.addAcc(username,password,account);
    }

    @GetMapping("/getacc/{id}")
    public ResponseEntity<Account> getAcc(@PathVariable int id)
    {
        return service.getAcc(id);
    }

//    @PostMapping("/hi")
//    public String hi() {
//
//        return jwtService.generateToken("musa");
//    }

}
