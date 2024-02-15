package org.car.rest.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestModelDto {
    @NotBlank
    @Size(min = 1, max = 255)
    @Schema(description = "This is car model name.", example = "Supra MK4")
    private String name;
    @NotBlank
    @Schema(description = "This is the maker name.", example = "Toyota")
    private String makeName;
}
