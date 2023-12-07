package com.example.airline.service;

import com.example.airline.model.User;
import com.example.airline.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserManager implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterUserResult registerNewAccount(User user) {
        if (user == null)  return new RegisterUserResult(Status.INVALID_USER);
        if (userRepository.findByEmail(user.getEmail()).isPresent())  return new RegisterUserResult(Status.EMAIL_ALREADY_REGISTERED);
        if(!user.getPassword().equals(user.getConfirmPassword())) return new RegisterUserResult(Status.PASSWORDS_DO_NOT_MATCH);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println(user.getEmail() + " " + user.getPassword());
        return new RegisterUserResult(Status.OK, user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(username).orElse(null);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return user;
    }

    public User getCurrentuser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return (User) this.loadUserByUsername(userName);
    }

    public static class RegisterUserResult {
        private  User user;
        private Status status;

        public RegisterUserResult(Status status,  User user) {
            this.status = status;
            this.user = user;
        }

        public RegisterUserResult(Status status) {
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public Status getStatus() {
            return status;
        }

        public boolean isOK() {
            return this.status == Status.OK;
        }

        @Override
        public String toString() {
            if (this.isOK()) return "Result is OK";
            return "Cannot perform the action cause: " + this.status.toString();
        }
    }
}
