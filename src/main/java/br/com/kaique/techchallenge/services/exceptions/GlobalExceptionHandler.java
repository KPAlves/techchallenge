package br.com.kaique.techchallenge.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException e) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, "Recurso nao encontrado", e.getMessage(), "/errors/not-found");
    }

    @ExceptionHandler({DuplicateEmailException.class, DuplicateLoginException.class})
    public ResponseEntity<ProblemDetail> handleDuplicateUsuarioException(RuntimeException e) {
        return buildProblemDetail(HttpStatus.CONFLICT, "Conflito de dados", e.getMessage(), "/errors/conflict");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return buildProblemDetail(HttpStatus.UNAUTHORIZED, "Credenciais invalidas", e.getMessage(), "/errors/invalid-credentials");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<>();
        for (var error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Existem campos invalidos na requisicao");
        problemDetail.setTitle("Dados invalidos");
        problemDetail.setType(URI.create("/errors/validation"));
        problemDetail.setProperty("errors", errors);

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return buildProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Requisicao invalida",
                "O corpo da requisicao esta malformado ou contem valores invalidos",
                "/errors/malformed-request"
        );
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(
            HttpStatus status,
            String title,
            String detail,
            String type
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setType(URI.create(type));

        return ResponseEntity.status(status).body(problemDetail);
    }
}
