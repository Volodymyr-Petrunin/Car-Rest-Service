package org.car.rest.domain;

import lombok.Getter;

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
    CROSSOVER("Crossover"),
    ;
    private final String name;

    Category(String name) {
        this.name = name;
    }

    private static final Map<String, Category> LABEL_TO_CATEGORY = Arrays.stream(values())
            .collect(Collectors.toMap(Category::getName, Function.identity()));

    public static Category valueOfLabel(String label){
        return LABEL_TO_CATEGORY.get(label.trim());
    }
}
