package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Car;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.CarDto;
import org.car.rest.domain.mapper.CarMapper;
import org.car.rest.repository.CarRepository;
import org.car.rest.repository.MakeRepository;
import org.car.rest.repository.ModelRepository;
import org.car.rest.specification.CarSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Autowired
    public CarService(CarRepository repository, MakeRepository makeRepository, ModelRepository modelRepository,
                      CarMapper carMapper) {
        this.repository = repository;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.carMapper = carMapper;
    }

    public List<CarDto> getAllCars() {
        return repository.findAll().stream().map(carMapper::carToCarDto).toList();
    }

    public CarDto getCarById(String id) {
        return carMapper.carToCarDto(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find car with id: " + id)));
    }

    public List<CarDto> getCarBySpecifications(CarDto carDto) {
        Specification<Car> specification = buildQuery(carDto);

        if (specification.equals(Specification.where(null))) {
            return Collections.emptyList();
        }

        return repository.findAll(specification).stream()
                .map(carMapper::carToCarDto).toList();
    }

    public CarDto createCar(CarDto carDto) {
        return carMapper.carToCarDto(repository.save(carMapper.carDtoToCar(carDto)));
    }

    public void createSeveralCars(Set<Car> cars) {
        setMakeInModel(cars);
        saveChildren(cars, makeRepository, car -> car.getModel().getMake());

        setModelInCar(cars);
        saveChildren(cars, modelRepository, Car::getModel);

        repository.saveAll(cars);
    }

    public CarDto updateCar(CarDto carDto){
        return carMapper.carToCarDto(repository.save(carMapper.carDtoToCar(carDto)));
    }

    public void deleteCarById(CarDto carDto) {
        repository.deleteById(carDto.getObjectId());
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

    private Specification<Car> buildQuery(CarDto carDto){
        Car car = carMapper.carDtoToCar(carDto);

        return Stream.of(
                        CarSpecifications.hasObjectId(car.getObjectId()),
                        CarSpecifications.hasYear(car.getYear()),
                        CarSpecifications.hasModel(car.getModel()),
                        CarSpecifications.hasCategories(car.getCategories()),
                        CarSpecifications.hasMaker(makeRepository.findByName(carDto.getMakeName()))
                )
                .flatMap(Optional::stream)
                .reduce(Specification::and)
                .orElse(Specification.where(null));
    }
}
