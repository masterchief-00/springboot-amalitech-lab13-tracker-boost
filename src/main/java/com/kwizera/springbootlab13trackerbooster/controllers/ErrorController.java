package com.kwizera.springbootlab13trackerbooster.controllers;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.Utils.CustomLogger;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.ApiErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        CustomLogger.log(CustomLogger.LogLevel.ERROR, ex.getMessage());
        ApiErrorResponse error = ApiErrorResponse
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomLogger.log(CustomLogger.LogLevel.ERROR, ex.getMessage());
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        CustomLogger.log(CustomLogger.LogLevel.ERROR, ex.getMessage());
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateRecordException(DuplicateRecordException ex) {
        CustomLogger.log(CustomLogger.LogLevel.ERROR, ex.getMessage());
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
