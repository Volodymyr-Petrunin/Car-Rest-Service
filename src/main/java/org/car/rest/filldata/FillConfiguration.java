package org.car.rest.filldata;

import com.opencsv.exceptions.CsvException;
import org.car.rest.mapper.CarMapper;
import org.car.rest.parser.CarParser;
import org.car.rest.reader.ResourceFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.List;

@Configuration
@PropertySource("classpath:fill-configuration.properties")
@Profile("fill-data")
public class FillConfiguration {

    @Bean
    public ResourceFileReader resourceFileReader(@Value("${filename}") String filename){
        return new ResourceFileReader(filename);
    }

    @Bean
    public List<String[]> csvData(ResourceFileReader reader) throws IOException, CsvException {
        return reader.read();
    }

    @Bean
    public CarParser carParser(List<String[]> csvData, CarMapper carMapper){
        return new CarParser(csvData, carMapper);
    }
}
