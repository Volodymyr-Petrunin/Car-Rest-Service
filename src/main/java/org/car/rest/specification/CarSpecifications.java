package org.car.rest.specification;

import jakarta.persistence.criteria.Join;
import org.car.rest.domain.Car;
import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;
import java.util.Optional;
import java.util.Set;

public final class CarSpecifications {

    public static Optional<Specification<Car>> hasYear(Year year){
        if (year == null) {
            return Optional.empty();
        }

        return Optional.of((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("year"), year));
    }

    public static Optional<Specification<Car>> hasModel(Model model){
        if (model == null) {
            return Optional.empty();
        }

        return Optional.of((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("model"), model));
    }

    public static Optional<Specification<Car>> hasCategories(Set<Category> categories){
        if (categories == null) {
            return Optional.empty();
        }

        return Optional.of((root, criteriaQuery, criteriaBuilder) -> {
            Join<Car, Set<Category>> categoriesJoin = root.join("categories");
            return categoriesJoin.in(categories);
        });
    }

    public static Optional<Specification<Car>> hasMaker(Make make){
        if (make == null) {
            return Optional.empty();
        }

        return Optional.of((root, query, criteriaBuilder) -> {
            Join<Car, Model> modelJoin = root.join("model");
            Join<Model, Make> makeJoin = modelJoin.join("make");

            return criteriaBuilder.equal(makeJoin.get("id"), make.getId());
        });
    }
}
