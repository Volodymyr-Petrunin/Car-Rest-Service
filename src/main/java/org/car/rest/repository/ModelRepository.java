package org.car.rest.repository;

import org.car.rest.domain.Make;
import org.car.rest.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findByNameAndMake(String name, Make make);
    boolean existsByNameAndMake(String name, Make make);
}
