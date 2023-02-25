package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> allUsers();
    public User show(long id);
    public Optional<User> getUserByName(String name);
    public void save(User user);
    public void update(long id,User updateUser);
    public void delete(long id);
}
