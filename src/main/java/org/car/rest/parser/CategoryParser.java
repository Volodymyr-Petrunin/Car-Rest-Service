package org.car.rest.parser;

import org.car.rest.domain.Category;
import org.car.rest.service.exception.CategoryNotExistException;
import org.car.rest.service.response.error.Code;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CategoryParser {
    private static final Map<String, Category> LABEL_TO_CATEGORY = Arrays.stream(Category.values())
            .collect(Collectors.toMap(Category::getName, Function.identity()));

    public Category valueOfLabel(String label) {
        if (label == null || label.isEmpty()) {
            throw new CategoryNotExistException(Code.REQUEST_VALIDATION_ERROR,
                    "Sorry your category label is empty.", "Category label is null or empty!", HttpStatus.BAD_REQUEST);
        }

        Category category = LABEL_TO_CATEGORY.get(label.trim());

        if (category == null) {
            category = parseWrongData(label)
                    .orElseThrow(() -> new CategoryNotExistException(
                        Code.REQUEST_VALIDATION_ERROR, "Sorry this category not exist.",
                        "Category with label " + label + " don't exist!", HttpStatus.BAD_REQUEST));
        }

        return category;
    }

    private Optional<Category> parseWrongData(String label){
        if (label.matches("SUV\\d{4}$")) {
            return Optional.of(Category.SUV);
        }

        // if you need add new parse logic

        return Optional.empty();
    }
}
