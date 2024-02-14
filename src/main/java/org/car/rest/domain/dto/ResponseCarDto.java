package org.car.rest.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.car.rest.domain.Category;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseCarDto {
    @Size(min = 10, max = 10)
    @Schema(description = "This is unique identifier for the car. Contain 10 chars.", example = "q1jEsACQIK")
    private String objectId;
    @Size(min = 0, max = 9999)
    @Schema(description = "This is the year the car was manufactured.", example = "2010")
    private short year;
    @Schema(description = "This is car model name.", example = "Mark MK4")
    private String modelName;
    @Schema(description = "This is the manufacturer's name.", example = "Toyota")
    private String makeName;
    @Schema(description = "These are car categories.")
    private Set<Category> categories;
}
