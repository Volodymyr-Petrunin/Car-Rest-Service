package org.car.rest.service.cri;

import org.car.rest.domain.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRepositoryConfiguration {

    @Bean
    public Class<Car> carClass(){
        return Car.class;
    }


}
