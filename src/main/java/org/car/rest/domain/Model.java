package org.car.rest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    public Model() {

    }

    public Model(Long id, String name, Make make) {
        this.id = id;
        this.name = name;
        this.make = make;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(id, model.id) && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
