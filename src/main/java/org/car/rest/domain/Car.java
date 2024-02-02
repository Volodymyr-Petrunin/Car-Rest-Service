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
@Table(name = "cars")
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
    @CollectionTable(name = "cars_categories", joinColumns = @JoinColumn(name = "car_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Set<Category> categories;

    public Car() {

    }

    public Car(String objectId, Year year, Model model, Set<Category> categories) {
        this.objectId = objectId;
        this.year = year;
        this.model = model;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Car{" +
                "objectId='" + objectId + '\'' +
                ", year=" + year +
                '}';
    }
}
