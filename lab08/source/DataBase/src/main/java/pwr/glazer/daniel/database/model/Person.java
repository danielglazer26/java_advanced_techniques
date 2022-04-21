package pwr.glazer.daniel.database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int personID;
    private String name;
    private String surname;

    public Person(){}

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
