package org.car.rest.filldata;

import jakarta.annotation.PostConstruct;
import org.car.rest.parser.CarParser;
import org.car.rest.service.CarService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
@Profile("fill-data")
public class CarFiller implements DataFiller {
    private final CarService carService;
    private final CarParser carParser;

    public CarFiller(CarService carService, CarParser carParser) {
        this.carService = carService;
        this.carParser = carParser;
    }

    @PostConstruct
    @Override
    public void fill() {
        carService.createSeveralCars(carParser.parse().collect(Collectors.toSet()));
    }
}
