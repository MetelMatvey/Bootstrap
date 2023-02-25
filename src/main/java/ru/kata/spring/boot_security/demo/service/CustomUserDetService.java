package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Optional;

@Service
public class CustomUserDetService implements UserDetailsService {
    private UserService userService;

    public CustomUserDetService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user.get();
    }
}
