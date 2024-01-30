package org.car.rest.domain.mapper;

import org.car.rest.domain.Car;
import org.car.rest.domain.convert.YearAttributeConverter;
import org.car.rest.domain.dto.ResponseCarDto;
import org.car.rest.domain.dto.RequestCarDto;
import org.car.rest.repository.MakeRepository;
import org.car.rest.repository.ModelRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {YearAttributeConverter.class})
public abstract class CarMapper {

    @Autowired
    private MakeRepository makeRepository;
    @Autowired
    private ModelRepository modelRepository;

    @Mapping(target = "modelName", source = "model.name")
    @Mapping(target = "makeName", source = "model.make.name")
    public abstract ResponseCarDto carToResponseCarDto(Car car);
    @Mapping(target = "year", source = "year", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    public abstract Car requestCarDtoToCar(RequestCarDto requestCarDto);

    @AfterMapping
    protected void setModelInCar(@MappingTarget Car car, RequestCarDto requestCarDto) {
        setModel(car, requestCarDto.getModelName(), requestCarDto.getMakeName());
    }

    private void setModel(Car car, String modelName, String makeName) {
        Optional.ofNullable(modelName)
                .flatMap(model -> Optional.ofNullable(makeName)
                        .map(make -> modelRepository.findByNameAndMake(modelName, makeRepository.findByName(makeName))))
                .ifPresent(car::setModel);
    }
}
