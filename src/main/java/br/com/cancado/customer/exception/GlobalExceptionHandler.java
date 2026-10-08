package br.com.cancado.customer.exception;

import br.com.cancado.customer.dto.StandardErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> handleGeneralException(Exception e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardErrorDTO err = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Erro interno do servidor",
                "Ocorreu um erro inesperado. Entre em contato com o suporte.",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    // Exceção personalizada de argumentos inválidos (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        StandardErrorDTO err = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Erro de validação",
                errorMessage,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<StandardErrorDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException e, HttpServletRequest request) {

        String error = "E-mail já foi utilizado";
        HttpStatus status = HttpStatus.CONFLICT;

        StandardErrorDTO err = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                error,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<StandardErrorDTO> handleInvalidCredentialsException(InvalidCredentialsException e, HttpServletRequest request) {

        String error = "E-mail ou senha incorretos";
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        StandardErrorDTO err = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                error,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<StandardErrorDTO> handleIOException(IOException e, HttpServletRequest request) {

        String error = "Failed or interrupted I/O operations";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardErrorDTO err = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                error,
                "Ocorreu uma falha ao processar a requisição. Tente novamente mais tarde.",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }
}

