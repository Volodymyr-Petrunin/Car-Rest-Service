package org.car.rest.service.cri;

import org.car.rest.domain.Car;
import org.car.rest.domain.Make;

import java.util.List;

public interface CustomCarRepository {
    List<Car> carBySpecifications(Car car, Make make);
}
