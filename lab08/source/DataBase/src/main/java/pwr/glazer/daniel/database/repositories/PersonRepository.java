package pwr.glazer.daniel.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pwr.glazer.daniel.database.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person,Integer>{
    Person getByPersonID(int personID);

    @Query("select p.personID from Person p where p.name = ?1 and p.surname = ?2")
    int getPersonIDByNameAndSurname(String name, String surname);
}

