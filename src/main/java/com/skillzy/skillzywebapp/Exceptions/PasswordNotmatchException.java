package com.skillzy.skillzywebapp.Exceptions;

public class PasswordNotmatchException extends RuntimeException{
    public PasswordNotmatchException(String message) {
        super(message);
    }
}
