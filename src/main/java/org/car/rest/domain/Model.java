package org.car.rest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    @Cascade(CascadeType.PERSIST)
    private Make make;

    public Model() {

    }

    public Model(Long id, String name, Make make) {
        this.id = id;
        this.name = name;
        this.make = make;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
