package org.car.rest.parser;

import org.car.rest.domain.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("file-parser")
public class FixedCategoryParser implements CategoryParser{
    private final CategoryParser categoryParser;

    public FixedCategoryParser(CategoryParser categoryParser) {
        this.categoryParser = categoryParser;
    }

    @Override
    public Category parse(String label) {
        if (label.matches("SUV\\d{4}$")) {
            return Category.SUV;
        }

        return categoryParser.parse(label);
    }
}
