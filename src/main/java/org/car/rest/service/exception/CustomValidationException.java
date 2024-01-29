package org.car.rest.service.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.car.rest.service.response.error.Code;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class CustomValidationException extends RuntimeException {
    private final Code code;
    private final String userMessage;
    private final String techMessage;
    private final HttpStatus httpStatus;

    protected CustomValidationException(Code code, String userMessage, String techMessage, HttpStatus httpStatus) {
        super(techMessage);
        this.code = code;
        this.userMessage = userMessage;
        this.techMessage = techMessage;
        this.httpStatus = httpStatus;
    }
}
