package org.car.rest.specification;

import jakarta.persistence.criteria.Join;
import org.car.rest.domain.Car;
import org.car.rest.domain.Category;
import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;
import java.util.Set;

public abstract class CarSpecifications {

    private CarSpecifications() {
        throw new IllegalStateException("Specifications class");
    }

    public static Specification<Car> carWithSameObjectId(String objectId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("objectId"), objectId);
    }

    public static Specification<Car> carWithSameYear(Year year){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("year"), year);
    }

    public static Specification<Car> carWithSameModel(Model model){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("model"), model);
    }

    public static Specification<Car> carWithSameCategories(Set<Category> categories){
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Car, Set<Category>> categoriesJoin = root.join("categories");
            return categoriesJoin.in(categories);
        };
    }

    public static Specification<Car> carWithSameMaker(Make make){
        return (root, query, criteriaBuilder) -> {
            Join<Car, Model> modelJoin = root.join("model");
            Join<Model, Make> makeJoin = modelJoin.join("make");

            return criteriaBuilder.equal(makeJoin.get("id"), make.getId());
        };
    }
}
