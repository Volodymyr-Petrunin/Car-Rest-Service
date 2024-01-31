package org.car.rest.parser;

import org.car.rest.domain.Category;

public interface CategoryParser {
    Category parse(String label);
}
