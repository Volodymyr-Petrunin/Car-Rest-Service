package org.car.rest.repository;

import org.car.rest.domain.Car;
import org.car.rest.repository.cri.CustomCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarRepository extends JpaRepository<Car, String>, JpaSpecificationExecutor<Car>, CustomCarRepository {

}
