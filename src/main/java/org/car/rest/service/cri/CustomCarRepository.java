package org.car.rest.service.cri;

import org.car.rest.domain.Car;
import org.car.rest.domain.dto.RequestCarDto;

import java.util.List;

public interface CustomCarRepository {
    List<Car> carsByRequestCarDto(RequestCarDto requestCarDto);
}
