package org.car.rest.service;

import org.car.rest.domain.Car;
import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.RequestCarDto;
import org.car.rest.domain.dto.ResponseCarDto;
import org.car.rest.domain.mapper.CarMapper;
import org.car.rest.genaration.ObjectIdGeneration;
import org.car.rest.repository.CarRepository;
import org.car.rest.service.exception.CarServiceException;
import org.car.rest.service.response.error.Code;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Sql(scripts = "classpath:scripts/car-service-script.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CarServiceTest {

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private ObjectIdGeneration objectIdGeneration;

    @MockBean
    private CarMapper carMapper;

    private final Make expectedMake = new Make(1L, "Mercedes-Benz");

    private final Model expectedModel = new Model(1L, "SLC", expectedMake);

    private final Set<Category> expectedCategories = Set.of(Category.SPORT, Category.CONVERTIBLE);

    private final Car expectedCar = new Car("abc", Year.of(2010), expectedModel, expectedCategories);

    private final RequestCarDto requestCarDto = new RequestCarDto();
    private final static ResponseCarDto expectedResponseCarDto = new ResponseCarDto();

    @BeforeAll
    static void beforeAll() {
        expectedResponseCarDto.setObjectId("abc");
        expectedResponseCarDto.setYear((short) 2010);
        expectedResponseCarDto.setModelName("SLC");
        expectedResponseCarDto.setMakeName("Mercedes-Benz");
        expectedResponseCarDto.setCategories(Set.of(Category.SPORT, Category.CONVERTIBLE));
    }

    @Test
    void testCreateCar_ShouldCreateCarWithoutException() {
        requestCarDto.setYear((short) 2010);
        requestCarDto.setCategories(expectedCategories);
        requestCarDto.setModelName(expectedModel.getName());
        requestCarDto.setMakeName(expectedMake.getName());

        when(carMapper.requestCarDtoToCar(requestCarDto)).thenReturn(expectedCar);
        when(objectIdGeneration.generateRandomChars()).thenReturn("abc");
        when(carRepository.save(expectedCar)).thenReturn(expectedCar);
        when(carMapper.carToResponseCarDto(expectedCar)).thenReturn(expectedResponseCarDto);

        ResponseCarDto actual = carService.createCar(requestCarDto);

        assertEquals(expectedResponseCarDto, actual);
    }

    @Test
    void testCreateCar_ShouldThrowErrorWhenModelIsNull() {
        requestCarDto.setYear((short) 2010);
        requestCarDto.setCategories(expectedCategories);
        requestCarDto.setModelName("M6"); // write wrong model name
        requestCarDto.setMakeName(expectedMake.getName());
        Car carAfterMapping = new Car("abc", Year.of(2010), null, expectedCategories);

        when(carMapper.requestCarDtoToCar(requestCarDto)).thenReturn(carAfterMapping);
        when(objectIdGeneration.generateRandomChars()).thenReturn("abc");

        CarServiceException exception = assertThrows(CarServiceException.class, () -> carService.createCar(requestCarDto));

        assertEquals(Code.REQUEST_VALIDATION_ERROR, exception.getCode());
        assertEquals("Sorry but your create request is not valid.", exception.getUserMessage());
        assertEquals("You can't create a Car with null values in fields.", exception.getTechMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());

        verify(carRepository, never()).save(any(Car.class));
    }
}