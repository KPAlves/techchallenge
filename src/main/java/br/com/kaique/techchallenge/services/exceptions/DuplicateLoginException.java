package br.com.kaique.techchallenge.services.exceptions;

public class DuplicateLoginException extends RuntimeException {
    public DuplicateLoginException(String message) {
        super(message);
    }
}
