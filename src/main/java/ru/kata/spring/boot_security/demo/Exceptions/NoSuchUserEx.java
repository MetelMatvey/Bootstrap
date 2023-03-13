package ru.kata.spring.boot_security.demo.Exceptions;

public class NoSuchUserEx extends RuntimeException {
    public NoSuchUserEx(String message) {
        super(message);
    }
}
