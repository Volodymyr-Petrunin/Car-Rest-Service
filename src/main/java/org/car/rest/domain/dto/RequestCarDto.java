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
public class RequestCarDto {
    @Size(min = 0, max = 9999)
    @Schema(description = "This is the year the car was manufactured.", example = "2010")
    private Short year;
    @Size(min = 0, max = 255)
    @Schema(description = "This is car model name. If it not null you must also add makeName", example = "null or Supra MK4")
    private String modelName;
    @Size(min = 0, max = 255)
    @Schema(description = "This is car maker name. If it not null you must also add modelName", example = "null or Toyota")
    private String makeName;
    @Schema(description = "These are car categories. All car categories include in application, you don't need add a new one.", example = "Convertible")
    private Set<Category> categories;
}
