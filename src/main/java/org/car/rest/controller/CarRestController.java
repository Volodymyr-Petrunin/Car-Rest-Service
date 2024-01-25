package org.car.rest.controller;

import org.car.rest.domain.dto.RequestCarDto;
import org.car.rest.domain.dto.ResponseCarDto;
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
    public List<ResponseCarDto> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseCarDto getCarById(@PathVariable String id){
        return carService.getCarById(id);
    }

    @GetMapping("/search")
    public List<ResponseCarDto> findCars(@RequestBody ResponseCarDto responseCarDto){
        return carService.getCarBySpecifications(responseCarDto);
    }

    @PatchMapping("/{objectId}")
    public ResponseCarDto updateCar(@PathVariable String objectId, @RequestBody RequestCarDto requestCarDto){
        return carService.updateCar(objectId, requestCarDto);
    }

    @DeleteMapping("/{objectId}")
    public void deleteCar(@PathVariable String objectId){
        carService.deleteCarById(objectId);
    }

    @PostMapping("/")
    public ResponseCarDto createCar(@RequestBody RequestCarDto requestCarDto){
        return carService.createCar(requestCarDto);
    }
}
