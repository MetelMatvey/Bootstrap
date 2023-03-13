package ru.kata.spring.boot_security.demo.Exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UserNameEx extends DataIntegrityViolationException {

    public UserNameEx(String message) {
        super(message);
    }
}
