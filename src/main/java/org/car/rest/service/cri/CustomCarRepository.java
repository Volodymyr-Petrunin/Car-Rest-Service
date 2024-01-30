package org.car.rest.service.cri;

import org.car.rest.domain.Car;
import org.car.rest.domain.Make;
import org.springframework.data.jpa.domain.Specification;

public interface CustomCarRepository {
    Specification<Car> carBySpecifications(Car car, Make make);
}
