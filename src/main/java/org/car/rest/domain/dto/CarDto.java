package org.car.rest.domain.dto;

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
public class CarDto {
    private String objectId;
    private short year;
    private String modelName;
    private String makeName;
    private Set<Category> categories;
}
