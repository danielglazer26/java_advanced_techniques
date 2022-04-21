package pwr.glazer.daniel.database.services;

import pwr.glazer.daniel.database.model.Person;

import java.util.List;

public interface PersonServiceInterface {
    List<Person> findAllPeople();
    Person getByPersonID(int personID);
    void saveAllPeople(List<Person> people);
    void savePerson(Person person);
}
