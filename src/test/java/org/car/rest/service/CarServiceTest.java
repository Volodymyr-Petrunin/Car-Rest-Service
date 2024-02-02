package org.car.rest.service;

import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.RequestCarDto;
import org.car.rest.domain.dto.ResponseCarDto;
import org.car.rest.genaration.ObjectIdGeneration;
import org.car.rest.service.exception.CarServiceException;
import org.car.rest.service.response.error.Code;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:scripts/car-service-script.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CarServiceTest {

    @Autowired
    private CarService carService;

    @MockBean
    private ObjectIdGeneration objectIdGeneration;

    private final Make expectedMake = new Make(1L, "Mercedes-Benz");

    private final Model expectedModel = new Model(1L, "SLC", expectedMake);

    private final Set<Category> expectedCategories = Set.of(Category.SPORT, Category.CONVERTIBLE);

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

        when(objectIdGeneration.generateRandomChars()).thenReturn("abc");

        ResponseCarDto actual = carService.createCar(requestCarDto);

        assertEquals(expectedResponseCarDto, actual);
    }

    @Test
    void testCreateCar_ShouldThrowErrorWhenModelIsNull() {
        requestCarDto.setYear((short) 2010);
        requestCarDto.setCategories(expectedCategories);
        requestCarDto.setModelName("M6"); // write wrong model name
        requestCarDto.setMakeName(expectedMake.getName());

        when(objectIdGeneration.generateRandomChars()).thenReturn("abc");

        CarServiceException exception = assertThrows(CarServiceException.class, () -> carService.createCar(requestCarDto));

        assertEquals(Code.REQUEST_VALIDATION_ERROR, exception.getCode());
        assertEquals("Sorry but your create request is not valid.", exception.getUserMessage());
        assertEquals("You can't create a Car with null values in fields.", exception.getTechMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
}