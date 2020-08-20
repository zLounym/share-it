package cz.zlounym.shareit.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cz.zlounym.shareit.exception.UsernameTakenException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameTakenException.class)
    public void handleUsernameTakenException(
            final UsernameTakenException exception,
            final HttpServletResponse response
    ) throws IOException {
        log.warn("UsernameTakenException", exception);
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public void handleDuplicateKeyException(final DuplicateKeyException exception, final HttpServletResponse response)
            throws IOException {
        log.warn("Duplicate Key!", exception);
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            final HttpMessageNotWritableException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        log.error("Error happened during object conversion", ex);
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(LocalDateTime.now())
                        .error("Conversion error")
                        .message(ex.getMessage())
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.debug("Malformed JSON request", ex);
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .error("Validation error")
                        .message(ex.getMessage())
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(
            final ConstraintViolationException exception,
            final WebRequest request
    ) {
        log.debug("Constraint violation occurred", exception);

        final List<String> errorFields = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getRootBeanClass().getName()
                        + ' '
                        + violation.getPropertyPath()
                        + ": "
                        + violation.getMessage()
                )
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .error("Validation error")
                        .message("Fields with errors: " + errorFields)
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        log.debug("Validation exception: ", ex);

        final List<String> errorFields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> (error.getField() + ": " + error.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .error("Validation error")
                        .message("Fields with errors: " + errorFields)
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .build());
    }

    private ErrorResponse createErrorDetails(final Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

}

