package com.bank.bankApp.service;

import com.bank.bankApp.model.Account;
import com.bank.bankApp.model.UserPrinciple;
import com.bank.bankApp.model.Users;
import com.bank.bankApp.repo.AccRepo;
import com.bank.bankApp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo repo;

    @Autowired
    AccRepo accRepo;
//    @Autowired
//    UserPrinciple principle;

//    @Autowired
//    AuthenticationManager manager;

    @Autowired
    JwtService service;

    public Users register(Users users) {
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
//        System.out.println(users);
        return repo.save(users);
    }

    public List<Users> getAll() {
        return repo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user= repo.findByUsername(username);
        if (user==null)
        {
//            System.out.println(user);
            throw new UsernameNotFoundException("User not found");
        }
//        System.out.println(user);
        return new UserPrinciple(user);
    }

    public String verify(String username,String password) {
//        System.out.println(users);
//        Authentication authentication=manager
//                .authenticate(new UsernamePasswordAuthenticationToken(username,password));
//        if (authentication.isAuthenticated())
//        {
//            return service.generateToken(loadUserByUsername(username));
//        }
        return "Failed to login";
    }

    public Users getUser(int id) {
        Optional<Users> users=repo.findById(id);
        Users users1=users.get();
        List<Account> accounts= accRepo.findByUsersId(id).stream().toList();
        users1.setAccounts(accounts);
        return users1;
    }

//    public ResponseEntity<Users> updateUser(Users users) {
//        Optional<Users> users1 = repo.findById(users.getId());
//        repo.de;
//    }

}
