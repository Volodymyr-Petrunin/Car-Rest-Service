package org.car.rest.repository.cri;

import org.car.rest.domain.Car;
import org.car.rest.domain.dto.RequestCarDto;

import java.util.List;

public interface CustomCarRepository {
    List<Car> carsByRequestCarDto(RequestCarDto requestCarDto);
}
