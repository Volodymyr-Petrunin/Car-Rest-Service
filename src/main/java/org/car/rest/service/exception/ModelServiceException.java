package org.car.rest.service.exception;

import org.car.rest.service.response.error.Code;
import org.springframework.http.HttpStatus;

public class ModelServiceException extends CustomValidationException {
    public ModelServiceException(Code code, String userMessage, String techMessage, HttpStatus httpStatus) {
        super(code, userMessage, techMessage, httpStatus);
    }
}
