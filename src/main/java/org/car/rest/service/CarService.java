package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Car;
import org.car.rest.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CarService {
    private final CarRepository repository;

    @Autowired
    public CarService(CarRepository repository) {
        this.repository = repository;
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

    public void createSeveralCars(List<Car> cars) {
        repository.saveAll(cars);
    }

    public void updateCar(Car car){
        repository.save(car);
    }

    public void deleteCarById(String id) {
        repository.deleteById(id);
    }
}
