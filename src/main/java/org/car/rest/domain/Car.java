package org.car.rest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Year;
import java.util.Set;

@Getter
@Setter
@Entity
public class Car {
    @Id
    @Column(name = "object_id", nullable = false)
    private String objectId;
    @Column(name = "car_year")
    @JdbcTypeCode(SqlTypes.SMALLINT)
    private Year year;
    @OneToOne
    private Model model;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "car_categories", joinColumns = @JoinColumn(name = "car_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Set<Category> categories;
}
