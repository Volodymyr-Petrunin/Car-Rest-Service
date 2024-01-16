package org.car.rest.mapper;

import org.car.rest.domain.Car;
import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class CarMapper {

    public Car mapToObject(String[] data) {
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

    private Set<Category> parseCategories(String categoriesAsString) {
        if (categoriesAsString.isEmpty()){
            return Collections.emptySet();
        }

        String[] categoriesArray = categoriesAsString.split(",");

        Set<Category> categories = new HashSet<>();

        for (String label : categoriesArray){
            categories.add(Category.valueOfLabel(label));
        }

        return categories;
    }
}
