package org.car.rest.service.exception;

import org.car.rest.service.response.error.Code;
import org.springframework.http.HttpStatus;

public class CategoryNotExistException extends CategoryValidationException {
    public CategoryNotExistException(String userMessage, String techMessage) {
        super(userMessage, techMessage);
    }
}
