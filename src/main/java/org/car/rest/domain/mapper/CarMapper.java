package org.car.rest.domain.mapper;

import org.car.rest.domain.Car;
import org.car.rest.domain.dto.CarDto;
import org.car.rest.repository.ModelRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;

@Mapper(componentModel = "spring", uses = ModelMapper.class)
public abstract class CarMapper {

    @Autowired
    private ModelRepository modelRepository;

    @Mapping(target = "modelName", source = "model.name")
    @Mapping(target = "makeName", source = "model.make.name")
    public abstract CarDto carToCarDto(Car car);
    public abstract Car carDtoToCar(CarDto carDto);

    @AfterMapping
    protected void setModelInCar(@MappingTarget Car car, CarDto carDto) {
        if (carDto.getModelName() != null) {
            car.setModel(modelRepository.findByName(carDto.getModelName()));
        }
    }

    protected short getYear(Year year) {
        return (short) year.getValue();
    }

    protected Year setYear(short year) {
        if (year == 0) {
            return null;
        }
        return Year.of(year);
    }
}
