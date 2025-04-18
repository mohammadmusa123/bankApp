package com.bank.bankApp.controller;

import com.bank.bankApp.model.Users;
import com.bank.bankApp.service.EmailService;
import com.bank.bankApp.service.JwtService;
import com.bank.bankApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService service;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users users)
    {
        Users users1= service.register(users);
        emailService.sendMail(users.getMail(),"USER Creation","Dear "+users.getUsername()+",\n\n    User Created Successfully.\n\nThank You ");
        return new ResponseEntity<>(users1,HttpStatus.OK);
    }

    @GetMapping("/get")
    public List<Users> getAll()
    {
        return service.getAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id)
    {
//        if (service.getUser(id) != null)
//        {
//            return new ResponseEntity<>(service.getUser(id),HttpStatus.OK);
//        }
        return new ResponseEntity<>(service.getUser(id),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/login")
    public String verify(@RequestParam String username,@RequestParam String password)
    {
//        System.out.println(users);
//        return service.verify(username,password);
        Authentication authentication=manager
                .authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if (authentication.isAuthenticated())
        {
            return jwtService.generateToken(service.loadUserByUsername(username));
        }
        return "User credential not found";
    }

//    @PostMapping("/update")
//    public ResponseEntity<Users> updateUser(@RequestBody Users users)
//    {
//        return service.updateUser(users);
//    }

}
