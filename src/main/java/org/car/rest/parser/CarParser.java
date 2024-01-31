package org.car.rest.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.car.rest.domain.Car;
import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.*;
import java.util.stream.Stream;

@Component
@PropertySource("classpath:fill-configuration.properties")
@Profile("fill-data")
public class CarParser implements Parser<Car> {
    private final String fileName;

    @Autowired
    public CarParser(@Value("${filename}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Stream<Car> parse() {
        return getCsvData().stream()
                .skip(1) // start with a second element because the first is header
                .filter(this::hasNonEmptyCell)
                .map(CarParser::mapToObject);
    }

    private List<String[]> getCsvData() {
        InputStream inputStream = getClass().getResourceAsStream("/" + fileName);

        assert inputStream != null;
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return read(reader);
        } catch (IOException e) {
            throw new RuntimeException("Something wrong in reader!", e);
        }
    }

    private List<String[]> read(Reader reader) throws IOException {
        try (CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll();
        } catch (CsvException e) {
            throw new IOException("Something wrong with cvsReader!", e);
        }
    }

    private static Car mapToObject(String[] data) {
        Car car = new Car();
        car.setObjectId(data[0]);
        car.setModel(new Model(null, data[3], new Make(null, data[1])));

        if (data[2].isEmpty()){
            car.setYear(Year.of(0));
        } else {
            car.setYear(Year.parse(data[2]));
        }

        car.setCategories(parseCategories(data[4]));

        return car;
    }

    private static Set<Category> parseCategories(String categoriesAsString) {
        if (categoriesAsString.isEmpty()){
            return Collections.emptySet();
        }

        String[] categoriesArray = categoriesAsString.split(",");

        Set<Category> categories = new HashSet<>();
        CategoryParser categoryParser = new CategoryParser();

        for (String label : categoriesArray){
            categories.add(categoryParser.valueOfLabel(label));
        }

        return categories;
    }

    private boolean hasNonEmptyCell(String[] cells) {
        return Arrays.stream(cells).noneMatch(cell -> cell == null || cell.trim().isEmpty());
    }
}
