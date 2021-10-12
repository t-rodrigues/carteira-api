package dev.thiagorodrigues.carteira.infra.handlers;

import dev.thiagorodrigues.carteira.application.exceptions.ErrorResponse;
import dev.thiagorodrigues.carteira.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        return buildValidationErrorResponse(ex, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred",
                request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildValidationErrorResponse(MethodArgumentNotValidException ex,
            HttpStatus status, String path) {
        var errorResponse = ErrorResponse.builder().status(status.value()).error(ex.getClass().getSimpleName())
                .message("Validation error").path(path).build();

        ex.getFieldErrors()
                .forEach(error -> errorResponse.addValidationError(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String message,
            String path) {
        var errorResponse = ErrorResponse.builder().status(status.value()).error(ex.getClass().getSimpleName())
                .message(message).path(path).build();

        return ResponseEntity.status(status).body(errorResponse);
    }

}
