package org.car.rest.service.cri;

import jakarta.persistence.EntityManager;
import org.car.rest.domain.Car;
import org.car.rest.domain.Make;
import org.car.rest.specification.CarSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CustomCarRepositoryImpl extends SimpleJpaRepository<Car, String> implements CustomCarRepository {

    public CustomCarRepositoryImpl(Class<Car> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
    }

    @Override
    public List<Car> carBySpecifications(Car car, Make make) {
        return super.findAll(buildQuery(car, make));
    }

    private Specification<Car> buildQuery(Car car, Make make){

        return Stream.of(
                        CarSpecifications.hasYear(car.getYear()),
                        CarSpecifications.hasModel(car.getModel()),
                        CarSpecifications.hasCategories(car.getCategories()),
                        CarSpecifications.hasMaker(make)
                )
                .flatMap(Optional::stream)
                .reduce(Specification::and)
                .orElse(Specification.where(null));
    }
}
