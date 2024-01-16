package org.car.rest.parser;

import org.car.rest.domain.Car;
import org.car.rest.mapper.CarMapper;

import java.util.List;
import java.util.stream.Stream;

public class CarParser implements Parser<Car> {

    private final List<String[]> csvData;
    private final CarMapper carMapper;

    public CarParser(List<String[]> csvData, CarMapper carMapper) {
        this.csvData = csvData;
        this.carMapper = carMapper;
    }

    @Override
    public Stream<Car> parse() {
        return csvData.stream()
                .skip(1) // start with second element because firs is header
                .map(carMapper::mapToObject);
    }
}
