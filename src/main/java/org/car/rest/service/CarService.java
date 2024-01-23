package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Car;
import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.CarDto;
import org.car.rest.domain.dto.ModelDto;
import org.car.rest.domain.mapper.CarMapper;
import org.car.rest.domain.mapper.ModelMapper;
import org.car.rest.repository.CarRepository;
import org.car.rest.repository.MakeRepository;
import org.car.rest.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarService {
    private final CarRepository repository;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    private final CarMapper carMapper;
    private final ModelService modelService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarService(CarRepository repository, MakeRepository makeRepository, ModelRepository modelRepository,
                      CarMapper carMapper, ModelService modelService,
                      ModelMapper modelMapper) {
        this.repository = repository;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.carMapper = carMapper;
        this.modelService = modelService;
        this.modelMapper = modelMapper;
    }

    public List<CarDto> getAllCars() {
        return repository.findAll().stream().map(carMapper::carToCarDto).toList();
    }

    public CarDto getCarById(String id) {
        return carMapper.carToCarDto(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find car with id: " + id)));
    }

    public List<CarDto> getCarByExample(CarDto carDto) {
        Example<Car> example = Example.of(carMapper.carDtoToCar(carDto));

        return repository.findAll(example).stream()
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
}
