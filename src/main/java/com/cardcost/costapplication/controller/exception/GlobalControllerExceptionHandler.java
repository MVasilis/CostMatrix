package com.cardcost.costapplication.controller.exception;

import com.cardcost.costapplication.service.exception.ServiceException;
import com.cardcost.costapplication.service.exception.ThirdPartyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Global controller exception handler.
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String TITLE = "title";
    private static final String STACKTRACE = "stacktrace";
    private static final String CODE = "code";
    private static final String DETAIL = "detail";
    private static final String SERVICE_EXCEPTION = "backend.E001";
    private static final String THIRD_PARTY_EXCEPTION = "backend.E002";
    private static final String BAD_REQUEST_ERROR = "backend.E998";
    private static final String THROWABLE_STATUS_I_AM_A_TEAPOT = "backend.E999";
    private static final String ERROR_MESSAGE_HANDLER = "Exception handler: ";


    /**
     * Handles generic {@link ServiceException}s.
     *
     * @param ex      the service exception
     * @param request the web request
     * @return a detailed web error about the exception
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleServiceException(ServiceException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put(TITLE, "Backend - Service exception");

        log.error(ERROR_MESSAGE_HANDLER, ex);
        body.put(STACKTRACE, ex.toString());
        body.put(CODE, SERVICE_EXCEPTION);
        body.put(DETAIL, ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    /**
     *  Handles generic {@link ThirdPartyException}s.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ThirdPartyException.class)
    public ResponseEntity handleTypeDocumentAlreadyExistException(ThirdPartyException ex, WebRequest request) {
        log.error(ERROR_MESSAGE_HANDLER, ex.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, Instant.now().toEpochMilli());
        body.put(STATUS, ((HttpClientErrorException)ex.getCause()).getStatusCode());
        body.put(TITLE, "Backend  - Document not found exception");
        body.put(CODE, THIRD_PARTY_EXCEPTION);
        body.put(DETAIL, ex.getMessage());
        body.put(STACKTRACE, ex.toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(body);
    }

    /**
     * Handles generic {@link MethodArgumentNotValidException}s.
     *
     * @param ex      the web exception
     * @param request the web request
     * @return a detailed web error about the exception
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        log.error(ERROR_MESSAGE_HANDLER, ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CODE, BAD_REQUEST_ERROR);
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(TITLE, HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put(DETAIL, ex.getLocalizedMessage());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(body);
    }

    /**
     * Handles any other {@link Throwable}s.
     *
     * @param ex      the web exception
     * @param request the web request
     * @return a detailed web error about the exception
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public ResponseEntity<Object> handleAnyException(Throwable ex, WebRequest request) {
        log.error(ERROR_MESSAGE_HANDLER, ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CODE, THROWABLE_STATUS_I_AM_A_TEAPOT);
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(TITLE, "Backend - Exception");
        body.put(DETAIL, ex.getMessage());
        body.put(STATUS, HttpStatus.I_AM_A_TEAPOT.value());
        body.put(STACKTRACE, ex.toString());

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).contentType(MediaType.APPLICATION_JSON).body(body);
    }

}
