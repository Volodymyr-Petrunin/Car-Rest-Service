package org.car.rest.parser;

import org.car.rest.domain.Category;
import org.car.rest.service.exception.CategoryNotExistException;
import org.car.rest.service.response.error.Code;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Primary
@Component
public class CategoryParserImpl implements CategoryParser {
    private static final Map<String, Category> LABEL_TO_CATEGORY = Arrays.stream(Category.values())
            .collect(Collectors.toMap(Category::getName, Function.identity()));

    @Override
    public Category parse(String label) {
        if (label == null || label.isEmpty()) {
            throw new CategoryNotExistException("Sorry your category label is empty.", "Category label is null or empty!");
        }

        Category category = LABEL_TO_CATEGORY.get(label.trim());

        if (category == null) {
            throw  new CategoryNotExistException("Sorry this category not exist.", "Category with label " + label + " don't exist!");
        }

        return category;
    }
}
