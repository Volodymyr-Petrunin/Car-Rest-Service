package org.car.rest.service.cri;

import jakarta.persistence.EntityManager;
import org.car.rest.domain.Car;
import org.car.rest.domain.Make;
import org.car.rest.domain.dto.RequestCarDto;
import org.car.rest.domain.mapper.CarMapper;
import org.car.rest.repository.MakeRepository;
import org.car.rest.specification.CarSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CustomCarRepositoryImpl extends SimpleJpaRepository<Car, String> implements CustomCarRepository {

    private final CarMapper carMapper;
    private final MakeRepository makeRepository;

    public CustomCarRepositoryImpl(Class<Car> domainClass, EntityManager entityManager, CarMapper carMapper, MakeRepository makeRepository) {
        super(domainClass, entityManager);
        this.carMapper = carMapper;
        this.makeRepository = makeRepository;
    }

    @Override
    public List<Car> carsByRequestCarDto(RequestCarDto requestCarDto) {
        return super.findAll(buildQuery(requestCarDto));
    }

    private Specification<Car> buildQuery(RequestCarDto requestCarDto){
        Car car = carMapper.requestCarDtoToCar(requestCarDto);
        Make make = makeRepository.findByName(requestCarDto.getMakeName());

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
