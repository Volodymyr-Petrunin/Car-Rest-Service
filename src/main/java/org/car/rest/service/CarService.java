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
import java.util.Set;
import java.util.function.Function;

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
        saveChildren(cars, makeRepository, car -> car.getModel().getMake());
        setMakeInModel(cars);
        saveChildren(cars, modelRepository, Car::getModel);
        setModelInCar(cars);

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

    private void setMakeInModel(Set<Car> cars){
        List<Make> makers = makeRepository.findAll();

        for (Make make : makers) {
            List<Car> carWithMake = cars.stream().filter(car -> car.getModel().getMake().getName().equals(make.getName())).toList();
            carWithMake.forEach(car -> car.getModel().setMake(make));
        }
    }

    private void setModelInCar(Set<Car> cars){
        List<Model> models = modelRepository.findAll();

        for (Model model : models) {
            List<Car> carWithModel = cars.stream().filter(car -> car.getModel().getName().equals(model.getName())).toList();
            carWithModel.forEach(car -> car.setModel(model));
        }
    }
}
