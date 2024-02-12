package org.car.rest.controller;

import org.car.rest.domain.Car;
import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.RequestCarDto;
import org.car.rest.domain.dto.ResponseCarDto;
import org.car.rest.service.CarService;
import org.car.rest.service.exception.CarServiceException;
import org.car.rest.service.response.error.Code;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    private static List<ResponseCarDto> expectedResponseDto = new ArrayList<>();
    private static List<RequestCarDto> expectedRequestDto = new ArrayList<>();

    private final String requestMapping = "/api/v1/car";

    @BeforeAll
    public static void setUp() {
        expectedResponseDto = setUpResponseCarDto();
        expectedRequestDto = setUpRequestCarDto();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testFindCars_ShouldReturnCorrectList_AndCallGetAllCarsMethod() throws Exception {
        RequestCarDto newDto = new RequestCarDto();
        when(carService.findCars(newDto)).thenReturn(expectedResponseDto);

        mockMvc.perform(get(requestMapping + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(newDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(expectedResponseDto)));

        verify(carService, times(1)).findCars(newDto);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testFindCars_ShouldFindCorrectCarByYear_AndCallCorrectMethods() throws Exception {
        RequestCarDto newDto = new RequestCarDto();
        newDto.setYear((short) 2020);
        when(carService.findCars(newDto)).thenReturn(List.of(expectedResponseDto.get(1)));

        mockMvc.perform(get(requestMapping + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(newDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(List.of(expectedResponseDto.get(1)))));

        verify(carService, times(1)).findCars(newDto);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testFindCars_ShouldFindCorrectCarByCategories_AndCallCorrectMethods() throws Exception {
        RequestCarDto newDto = new RequestCarDto();
        newDto.setCategories(Set.of(Category.SPORT));
        when(carService.findCars(newDto)).thenReturn(List.of(expectedResponseDto.get(2)));

        mockMvc.perform(get(requestMapping + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(newDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(List.of(expectedResponseDto.get(2)))));

        verify(carService, times(1)).findCars(newDto);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testFindByObjectId_ShouldReturnCorrectObject_AndCallGetCarByIdMethod() throws Exception {
        String objectId = expectedResponseDto.get(0).getObjectId();
        when(carService.getCarById(objectId)).thenReturn(expectedResponseDto.get(0));

        mockMvc.perform(get(requestMapping + "/" + objectId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(expectedResponseDto.get(0))));

        verify(carService, times(1)).getCarById(objectId);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateCar_ShouldReturnUpdatedCar() throws Exception {
        RequestCarDto requestCarDto = expectedRequestDto.get(0);
        String objectId = "golf";
        requestCarDto.setYear((short) 1995);

        ResponseCarDto responseCarDto = expectedResponseDto.get(0);
        responseCarDto.setObjectId(objectId);
        responseCarDto.setYear((short) 1995);

        when(carService.updateCar(objectId, requestCarDto)).thenReturn(responseCarDto);

        mockMvc.perform(patch(requestMapping + "/" + objectId)
                        .param("objectId", objectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(requestCarDto))
                            .with(csrf())
                            .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(responseCarDto)));

        verify(carService, times(1)).updateCar(objectId, requestCarDto);
    }

    @Test
    void testDeleteCar_ShouldReturnNoContent() throws Exception {
        String objectId = "abc";

        mockMvc.perform(delete(requestMapping  + "/" + objectId)
                    .with(csrf())
                    .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());

        verify(carService, times(1)).deleteCarById(objectId);
    }

    @Test
    void testCreateCar_ShouldReturnCreatedCar() throws Exception {
        RequestCarDto requestCarDto = new RequestCarDto();
        requestCarDto.setYear((short) 2022);

        ResponseCarDto responseCarDto = new ResponseCarDto();
        responseCarDto.setObjectId("any");
        requestCarDto.setYear((short) 2022);

        when(carService.createCar(requestCarDto)).thenReturn(responseCarDto);

        mockMvc.perform(post(requestMapping + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(requestCarDto))
                            .with(csrf())
                            .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(responseCarDto)));

        verify(carService, times(1)).createCar(requestCarDto);
    }

    @Test
    void testCreateCar_ShouldReturnBadRequestOnNullFields() throws Exception{
        RequestCarDto requestCarDto = expectedRequestDto.get(0);
        requestCarDto.setModelName(null);

        CarServiceException expectedException = new CarServiceException(
                Code.REQUEST_VALIDATION_ERROR,
                "Sorry but your create request is not valid.",
                "You can't create a Car with null values in fields.",
                HttpStatus.BAD_REQUEST
        );

        when(carService.createCar(requestCarDto)).thenThrow(expectedException);

        mockMvc.perform(post(requestMapping + "/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapToJson(requestCarDto))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error.code").value(expectedException.getCode().toString()))
                .andExpect(jsonPath("$.error.userMessage").value(expectedException.getUserMessage()))
                .andExpect(jsonPath("$.error.techMessage").value(expectedException.getTechMessage()));

        verify(carService, times(1)).createCar(requestCarDto);
    }

    private static List<ResponseCarDto> setUpResponseCarDto(){
        return expectedCars.stream()
                .map(car -> {
                    ResponseCarDto responseCarDto = new ResponseCarDto();
                    responseCarDto.setObjectId(car.getObjectId());
                    responseCarDto.setYear((short) car.getYear().getValue());
                    responseCarDto.setModelName(car.getModel().getName());
                    responseCarDto.setMakeName(car.getModel().getMake().getName());
                    responseCarDto.setCategories(car.getCategories());

                    return responseCarDto;
                }).toList();
    }

    private static List<RequestCarDto> setUpRequestCarDto(){
        return expectedCars.stream()
                .map(car -> {
                    RequestCarDto requestCarDto = new RequestCarDto();
                    requestCarDto.setYear((short) car.getYear().getValue());
                    requestCarDto.setModelName(car.getModel().getName());
                    requestCarDto.setMakeName(car.getModel().getMake().getName());
                    requestCarDto.setCategories(car.getCategories());

                    return requestCarDto;
                }).toList();
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}