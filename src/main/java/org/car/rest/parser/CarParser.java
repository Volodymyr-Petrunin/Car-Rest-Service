package org.car.rest.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.car.rest.domain.Car;
import org.car.rest.mapper.CarMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

public class CarParser implements Parser<Car> {
    private final String fileName;
    private final CarMapper carMapper;

    public CarParser(String fileName, CarMapper carMapper) {
        this.fileName = fileName;
        this.carMapper = carMapper;
    }

    @Override
    public Stream<Car> parse() {
        return getCsvData().stream()
                .skip(1) // start with second element because firs is header
                .map(carMapper::mapToObject);
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
}
