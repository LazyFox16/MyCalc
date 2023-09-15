package ru.lazyfox16.mycalc.exceptions;

public class UnrecognizedTokenException extends RuntimeException {

    public UnrecognizedTokenException(String message) {
        super(message);
    }
}
