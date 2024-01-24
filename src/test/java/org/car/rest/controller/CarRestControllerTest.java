package org.car.rest.controller;

import org.car.rest.domain.Car;
import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.CarDto;
import org.car.rest.service.CarService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarRestController.class)
class CarRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;

    private static final List<Car> expectedCars = List.of(
            new Car("abc", Year.of(2005),
                    new Model(null, "Golf 2", new Make(null, "Volkswagen")), Set.of(Category.HATCHBACK)),
            new Car("qwerty", Year.of(2020),
                    new Model(null, "XC60", new Make(null, "Volvo")), Set.of(Category.CROSSOVER, Category.SUV)),
            new Car("zxcvb", Year.of(2010),
                    new Model(null, "R172", new Make(null, "Mercedes-Benz")), Set.of(Category.SPORT, Category.CONVERTIBLE))
    );

    private final static List<CarDto> expectedDto = new ArrayList<>();

    private final String requestMapping = "/api/v1/car";

    @BeforeAll
    public static void setUp() {
        for (Car car : expectedCars){
            CarDto carDto = new CarDto();
            carDto.setObjectId(car.getObjectId());
            carDto.setYear(carDto.getYear());
            carDto.setModelName(car.getModel().getName());
            carDto.setMakeName(car.getModel().getMake().getName());
            carDto.setCategories(car.getCategories());

            expectedDto.add(carDto);
        }
    }

    @Test
    void testFindAll_ShouldReturnCorrectList_AndCallGetAllCarsMethod() throws Exception {
        when(carService.getAllCars()).thenReturn(expectedDto);

        mockMvc.perform(get(requestMapping + "/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(expectedDto)));

        verify(carService, times(1)).getAllCars();
    }

    @Test
    void testFindByObjectId_ShouldReturnCorrectObject_AndCallGetCarByIdMethod() throws Exception {
        String objectId = expectedDto.get(0).getObjectId();
        when(carService.getCarById(objectId)).thenReturn(expectedDto.get(0));

        mockMvc.perform(get(requestMapping + "/" + objectId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(expectedDto.get(0))));

        verify(carService, times(1)).getCarById(objectId);
    }

    @Test
    void testSearch_ShouldFindCorrectCarByYear_AndCallCorrectMethods() throws Exception {
        CarDto newDto = new CarDto();
        newDto.setYear((short) 2020);
        when(carService.getCarBySpecifications(newDto)).thenReturn(List.of(expectedDto.get(1)));

        mockMvc.perform(get(requestMapping + "/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(newDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(List.of(expectedDto.get(1)))));

        verify(carService, times(1)).getCarBySpecifications(newDto);
    }

    @Test
    void testSearch_ShouldFindCorrectCarByCategories_AndCallCorrectMethods() throws Exception {
        CarDto newDto = new CarDto();
        newDto.setCategories(Set.of(Category.SPORT));
        when(carService.getCarBySpecifications(newDto)).thenReturn(List.of(expectedDto.get(2)));

        mockMvc.perform(get(requestMapping + "/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(newDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(List.of(expectedDto.get(2)))));

        verify(carService, times(1)).getCarBySpecifications(newDto);
    }

    @Test
    void testUpdateCar_ShouldReturnUpdatedCar() throws Exception {
        CarDto carDto = expectedDto.get(0);
        carDto.setObjectId("golf");
        carDto.setYear((short) 1995);
        when(carService.updateCar(carDto)).thenReturn(carDto);

        mockMvc.perform(patch(requestMapping + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(carDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(carDto)));

        verify(carService, times(1)).updateCar(carDto);
    }

    @Test
    void testDeleteCar_ShouldReturnNoContent() throws Exception {
        CarDto carDto = expectedDto.get(0);

        mockMvc.perform(delete(requestMapping  + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(carDto)))
                .andExpect(status().isOk());

        verify(carService, times(1)).deleteCarById(carDto);
    }

    @Test
    void testCreateCar_ShouldReturnCreatedCar() throws Exception {
        CarDto carDto = new CarDto();
        carDto.setYear((short) 2022);

        when(carService.createCar(carDto)).thenReturn(carDto);

        mockMvc.perform(put(requestMapping + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(carDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(carDto)));

        verify(carService, times(1)).createCar(carDto);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}