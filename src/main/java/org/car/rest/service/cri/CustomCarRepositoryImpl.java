package org.car.rest.service.cri;

import org.car.rest.domain.Car;
import org.car.rest.domain.Make;
import org.car.rest.specification.CarSpecifications;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

public class CustomCarRepositoryImpl implements CustomCarRepository {
    @Override
    public Specification<Car> carBySpecifications(Car car, Make make) {
        return buildQuery(car, make);
    }

    private Specification<Car> buildQuery(Car car, Make make){

        return Stream.of(
                        CarSpecifications.hasYear(car.getYear()),
                        CarSpecifications.hasModel(car.getModel()),
                        CarSpecifications.hasCategories(car.getCategories()),
                        CarSpecifications.hasMaker(make)
                )
                .flatMap(Optional::stream)
                .reduce(Specification::and)
                .orElse(Specification.where(null));
    }
}
