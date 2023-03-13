package ru.kata.spring.boot_security.demo.Exceptions;

public class ExceptionData {

    private String info;

    public ExceptionData() {
    }

    public ExceptionData(String message) {
        info = message;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
