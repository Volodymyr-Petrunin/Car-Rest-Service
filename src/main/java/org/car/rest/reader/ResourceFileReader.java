package org.car.rest.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResourceFileReader implements org.car.rest.reader.Reader {
    private final String fileName;

    public ResourceFileReader(String fileName) {
        this.fileName = fileName;
    }

    public List<String[]> read() throws IOException{
        InputStream inputStream = getClass().getResourceAsStream("/" + fileName);

        assert inputStream != null;
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            } catch (CsvException e) {
                throw new IOException("Something wrong with ResourceFileReader!", e);
            }
        }
    }
}
