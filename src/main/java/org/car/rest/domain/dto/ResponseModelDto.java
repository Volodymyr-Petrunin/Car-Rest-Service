package org.car.rest.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseModelDto {
    private Long id;
    private String name;
    private String makeName;
}
