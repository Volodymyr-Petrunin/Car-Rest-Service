package org.car.rest.controller;

import org.car.rest.domain.dto.CarDto;
import org.car.rest.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
public class CarRestController {
    private final CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    public List<CarDto> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable String id){
        return carService.getCarById(id);
    }

    @GetMapping("/search")
    public List<CarDto> findCars(@RequestBody CarDto carDto){
        return carService.getCarBySpecifications(carDto);
    }

    @PatchMapping("/")
    public CarDto updateCar(@RequestBody CarDto carDto){
        return carService.updateCar(carDto);
    }

    @DeleteMapping("/")
    public void deleteCar(@RequestBody CarDto carDto){
        carService.deleteCarById(carDto);
    }

    @PutMapping("/")
    public CarDto createCar(@RequestBody CarDto carDto){
        return carService.createCar(carDto);
    }

}
