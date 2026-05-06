package br.com.kaique.techchallenge.services.exceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
