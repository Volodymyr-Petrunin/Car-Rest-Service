package org.car.rest.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseModelDto {
    @Schema(description = "This is unique identifier for the model.", example = "1")
    private Long id;
    @Schema(description = "This is car model name.", example = "Mark MK4")
    private String name;
    @Schema(description = "This is the maker name.", example = "Toyota")
    private String makeName;
}
