package org.car.rest.service;

import org.car.rest.service.exception.CarServiceException;
import org.car.rest.service.exception.MakeServiceException;
import org.car.rest.service.response.error.Error;
import org.car.rest.service.response.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerService {

    @ExceptionHandler(CarServiceException.class)
    public ResponseEntity<ErrorResponse> handleCarServiceException(CarServiceException exception){
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                .code(exception.getCode())
                .userMessage(exception.getUserMessage())
                .techMessage(exception.getTechMessage())
                .build()).build(), exception.getHttpStatus());
    }

    @ExceptionHandler(MakeServiceException.class)
    public ResponseEntity<ErrorResponse> handleMakeServiceException(MakeServiceException exception){
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                .code(exception.getCode())
                .userMessage(exception.getUserMessage())
                .techMessage(exception.getTechMessage())
                .build()).build(), exception.getHttpStatus());
    }
}
