package org.car.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.car.rest.domain.dto.RequestCarDto;
import org.car.rest.domain.dto.ResponseCarDto;
import org.car.rest.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
@SecurityRequirement(name = "bearerAuth")
public class CarRestController {
    private final CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    @Operation(
            summary = "Retrieve information about cars",
            description = "If searching by model, you must specify the manufacturer's name. "
                    + "Any else feel free to do."
    )
    public List<ResponseCarDto> findCars(@RequestBody RequestCarDto requestCarDto){
        return carService.findCars(requestCarDto);
    }

    @GetMapping("/{objectId}")
    public ResponseCarDto getCarById(@PathVariable String objectId){
        return carService.getCarById(objectId);
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
