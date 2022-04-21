package pwr.glazer.daniel.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pwr.glazer.daniel.database.model.Person;


public interface PersonRepository extends CrudRepository<Person,Integer>{
    Person getByPersonID(int personID);
}

