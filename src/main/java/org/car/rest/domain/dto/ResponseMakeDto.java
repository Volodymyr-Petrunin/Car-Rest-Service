package org.car.rest.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMakeDto {
    @Schema(description = "This is unique identifier for the maker.", example = "1")
    private Long id;
    @Schema(description = "This is the manufacturer's name.", example = "Toyota")
    private String name;
}
