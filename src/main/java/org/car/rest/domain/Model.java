package org.car.rest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model_gen")
    @SequenceGenerator(name = "model_gen", sequenceName = "model_seq")
    @Column(name = "model_id", nullable = false)
    private Long id;
    @Column(name = "model_name")
    private String name;
    @OneToOne
    private Make make;
}
