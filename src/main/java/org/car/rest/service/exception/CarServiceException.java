package org.car.rest.service.exception;

import lombok.Builder;
import lombok.Data;
import org.car.rest.service.response.error.Code;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class CarServiceException extends RuntimeException {
    private final Code code;
    private final String userMessage;
    private final String techMessage;
    private final HttpStatus httpStatus;

    public CarServiceException(Code code, String userMessage, String techMessage, HttpStatus httpStatus) {
        this.code = code;
        this.userMessage = userMessage;
        this.techMessage = techMessage;
        this.httpStatus = httpStatus;
    }
}
