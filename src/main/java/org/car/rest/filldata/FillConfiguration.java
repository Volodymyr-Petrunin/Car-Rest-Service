package org.car.rest.filldata;

import org.car.rest.mapper.CarMapper;
import org.car.rest.parser.CarParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:fill-configuration.properties")
@Profile("fill-data")
public class FillConfiguration {
    @Bean
    public CarParser carParser(@Value("${filename}") String filename, CarMapper carMapper){
        return new CarParser(filename, carMapper);
    }
}
