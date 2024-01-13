package org.car.rest.domain;


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

    public String getName() {
        return name;
    }
}
