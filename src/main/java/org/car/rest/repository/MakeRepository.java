package org.car.rest.repository;

import org.car.rest.domain.Make;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MakeRepository extends JpaRepository<Make, Long> {
}
