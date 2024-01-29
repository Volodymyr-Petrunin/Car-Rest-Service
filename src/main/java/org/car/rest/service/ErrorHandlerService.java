package org.car.rest.service;

import org.car.rest.service.exception.*;
import org.car.rest.service.response.error.Code;
import org.car.rest.service.response.error.Error;
import org.car.rest.service.response.error.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerService {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleCarServiceException(CustomValidationException exception){
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                .code(exception.getCode())
                .userMessage(exception.getUserMessage())
                .techMessage(exception.getTechMessage())
                .build()).build(), exception.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .error(Error.builder()
                                .code(Code.REQUEST_VALIDATION_ERROR)
                                .userMessage("This record violates a unique constraint.")
                                .techMessage(exception.getMessage())
                                .build())
                        .build());
    }
}
