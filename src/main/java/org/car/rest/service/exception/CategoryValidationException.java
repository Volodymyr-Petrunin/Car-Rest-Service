package org.car.rest.service.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class CategoryValidationException extends RuntimeException {
    private final String userMessage;
    private final String techMessage;

    public CategoryValidationException(String userMessage, String techMessage) {
        super(techMessage);
        this.userMessage = userMessage;
        this.techMessage = techMessage;
    }
}
