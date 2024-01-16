package org.car.rest.parser;

import java.util.stream.Stream;

public interface Parser<T> {
    Stream<T> parse();
}
