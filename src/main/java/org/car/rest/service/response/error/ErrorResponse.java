package org.car.rest.service.response.error;

import lombok.Builder;
import lombok.Data;
import org.car.rest.service.response.Response;

@Data
@Builder
public class ErrorResponse implements Response {
    private Error error;
}
