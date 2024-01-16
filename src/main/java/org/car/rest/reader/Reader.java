package org.car.rest.reader;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface Reader {
    List<String[]> read() throws IOException, CsvException;
}
