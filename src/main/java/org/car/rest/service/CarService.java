package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Car;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.RequestCarDto;
import org.car.rest.domain.dto.ResponseCarDto;
import org.car.rest.domain.mapper.CarMapper;
import org.car.rest.genaration.ObjectIdGeneration;
import org.car.rest.repository.CarRepository;
import org.car.rest.repository.MakeRepository;
import org.car.rest.repository.ModelRepository;
import org.car.rest.service.exception.CarServiceException;
import org.car.rest.service.response.error.Code;
import org.car.rest.specification.CarSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class CarService {
    private final CarRepository repository;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    private final CarMapper carMapper;
    private final ObjectIdGeneration objectIdGeneration;

    @Autowired
    public CarService(CarRepository repository, MakeRepository makeRepository, ModelRepository modelRepository,
                      CarMapper carMapper, ObjectIdGeneration objectIdGeneration) {
        this.repository = repository;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.carMapper = carMapper;
        this.objectIdGeneration = objectIdGeneration;
    }

    public List<ResponseCarDto> getAllCars() {
        return repository.findAll().stream().map(carMapper::carToResponseCarDto).toList();
    }

    public ResponseCarDto getCarById(String id) {
        return carMapper.carToResponseCarDto(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find car with id: " + id)));
    }

    public List<ResponseCarDto> getCarBySpecifications(RequestCarDto requestCarDto) {
        Specification<Car> specification = buildQuery(requestCarDto);

        return repository.findAll(specification).stream()
                .map(carMapper::carToResponseCarDto).toList();
    }

    public ResponseCarDto createCar(RequestCarDto requestCarDto) {
        Car car = carMapper.requestCarDtoToCar(requestCarDto);
        car.setObjectId(objectIdGeneration.generateRandomChars());

        nullValidation(car);

        return carMapper.carToResponseCarDto(repository.save(car));
    }

    public void createSeveralCars(Set<Car> cars) {
        setMakeInModel(cars);
        saveChildren(cars, makeRepository, car -> car.getModel().getMake());

        setModelInCar(cars);
        saveChildren(cars, modelRepository, Car::getModel);

        repository.saveAll(cars);
    }

    public ResponseCarDto updateCar(String objectId, RequestCarDto requestCarDto){
        Car car = carMapper.requestCarDtoToCar(requestCarDto);
        car.setObjectId(objectId);

        nullValidation(car);

        return carMapper.carToResponseCarDto(repository.save(car));
    }

    public void deleteCarById(String objectId) {
        repository.deleteById(objectId);
    }

    private <T> void saveChildren(Set<Car> cars, JpaRepository<T, Long> repo, Function<Car, T> function) {
        repo.saveAll(cars.stream().map(function).distinct().toList());
    }

    private void setMakeInModel(Set<Car> cars) {
        Map<String, Make> makeMap = cars.stream()
                .map(car -> car.getModel().getMake())
                .distinct()
                .collect(Collectors.toMap(Make::getName, Function.identity()));

        cars.forEach(car -> car.getModel().setMake(makeMap.get(car.getModel().getMake().getName())));
    }

    private void setModelInCar(Set<Car> cars){
        Map<String, Model> modelMap = cars.stream()
                .map(Car::getModel)
                .distinct()
                .collect(Collectors.toMap(Model::getName, Function.identity()));

        cars.forEach(car -> car.setModel(modelMap.get(car.getModel().getName())));
    }

    private Specification<Car> buildQuery(RequestCarDto requestCarDto){
        Car car = carMapper.requestCarDtoToCar(requestCarDto);

        return Stream.of(
                        CarSpecifications.hasYear(car.getYear()),
                        CarSpecifications.hasModel(car.getModel()),
                        CarSpecifications.hasCategories(car.getCategories()),
                        CarSpecifications.hasMaker(makeRepository.findByName(requestCarDto.getMakeName()))
                )
                .flatMap(Optional::stream)
                .reduce(Specification::and)
                .orElse(Specification.where(null));
    }

    private void nullValidation(Car car){
        if (hasNullFields(car)) {
            throw new CarServiceException(Code.REQUEST_VALIDATION_ERROR,
                    "Sorry but your create request is not valid.", "You can't create a Car with null values in fields.", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean hasNullFields(Car car){
        return car.getYear() == null ||
                car.getModel() == null ||
                car.getCategories() == null ||
                car.getCategories().isEmpty();
    }
}
