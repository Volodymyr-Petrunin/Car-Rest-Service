package org.car.rest.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@ComponentScan(basePackages = {"org.car.rest.service", "org.car.rest.domain.mapper",
            "org.car.rest.repository.cri", "org.car.rest.domain.convert"})
@EntityScan(basePackages = "org.car.rest.domain")
@EnableJpaRepositories(basePackages = "org.car.rest.repository")
@EnableAutoConfiguration
public class ServiceTestConfiguration {
}
