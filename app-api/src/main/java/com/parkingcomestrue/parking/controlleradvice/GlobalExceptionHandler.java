package com.parkingcomestrue.parking.controlleradvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.parkingcomestrue.parking.controlleradvice.dto.ExceptionResponse;
import com.parkingcomestrue.parking.application.exception.ClientException;
import com.parkingcomestrue.parking.support.exception.DomainException;
import com.parkingcomestrue.parking.application.exception.ClientExceptionInformation;
import java.util.EnumMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final EnumMap<ClientExceptionInformation, HttpStatus> exceptionInfoToHttpStatus = new EnumMap<>(
            ClientExceptionInformation.class);

    public GlobalExceptionHandler() {
        exceptionInfoToHttpStatus.put(ClientExceptionInformation.UNAUTHORIZED, UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(final Exception e) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("알지 못하는 예외 발생");
        log.error("알지 못하는 예외 발생", e);

        return ResponseEntity.internalServerError()
                .body(exceptionResponse);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionResponse> handleServerException(final DomainException e) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        log.error(e.getMessage(), e);

        return ResponseEntity.internalServerError()
                .body(exceptionResponse);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ExceptionResponse> handleClientException(final ClientException e) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());

        final HttpStatus httpStatus = exceptionInfoToHttpStatus.getOrDefault(e.getExceptionInformation(), BAD_REQUEST);

        return ResponseEntity.status(httpStatus)
                .body(exceptionResponse);
    }
}
