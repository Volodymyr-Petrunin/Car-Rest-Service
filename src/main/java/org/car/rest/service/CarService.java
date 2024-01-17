package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Car;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.car.rest.repository.CarRepository;
import org.car.rest.repository.MakeRepository;
import org.car.rest.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CarService(CarRepository repository, MakeRepository makeRepository, ModelRepository modelRepository) {
        this.repository = repository;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
    }

    public List<Car> getAllCars() {
        return repository.findAll();
    }

    public Car getCarById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find car with id: " + id));
    }

    public void createCar(Car car) {
        repository.save(car);
    }

    public void createSeveralCars(Set<Car> cars) {
        setMakeInModel(cars);
        saveChildren(cars, makeRepository, car -> car.getModel().getMake());

        setModelInCar(cars);
        saveChildren(cars, modelRepository, Car::getModel);

        repository.saveAll(cars);
    }

    public void updateCar(Car car){
        repository.save(car);
    }

    public void deleteCarById(String id) {
        repository.deleteById(id);
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
