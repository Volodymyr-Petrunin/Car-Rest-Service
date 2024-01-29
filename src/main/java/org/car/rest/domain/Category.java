package org.car.rest.domain;

import lombok.Getter;
import org.car.rest.service.exception.CategoryNotExistException;
import org.car.rest.service.response.error.Code;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Category {
    SUV("SUV"),
    SEDAN("Sedan"),
    COUPE("Coupe"),
    CONVERTIBLE("Convertible"),
    PICKUP("Pickup"),
    VAN("Van/Minivan"),
    SPORT("Sport"),
    HATCHBACK("Hatchback"),
    WAGON("Wagon"),
    CROSSOVER("Crossover");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    private static final Map<String, Category> LABEL_TO_CATEGORY = Arrays.stream(values())
            .collect(Collectors.toMap(Category::getName, Function.identity()));

    public static Category valueOfLabel(String label) {
        if (label == null || label.isEmpty()) {
            throw new CategoryNotExistException(Code.REQUEST_VALIDATION_ERROR,
                    "Sorry your category label is empty.", "Category label is null or empty!", HttpStatus.BAD_REQUEST);
        }

        Category category = LABEL_TO_CATEGORY.get(label.trim());

        if (category == null) {
            throw new CategoryNotExistException(Code.REQUEST_VALIDATION_ERROR,
                    "Sorry this category not exist.", "Category with label " + label + " don't exist!", HttpStatus.BAD_REQUEST);
        }

        return category;
    }
}
