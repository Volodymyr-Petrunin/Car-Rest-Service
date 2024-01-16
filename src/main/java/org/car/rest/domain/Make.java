package org.car.rest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "makes")
public class Make {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "make_gen")
    @SequenceGenerator(name = "make_gen", sequenceName = "make_seq")
    @Column(name = "make_id", nullable = false)
    private Long id;
    @Column(name = "make_model")
    private String name;

    public Make() {

    }

    public Make(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Make{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
