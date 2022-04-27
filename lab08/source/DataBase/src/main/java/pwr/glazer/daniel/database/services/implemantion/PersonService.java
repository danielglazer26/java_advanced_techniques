package pwr.glazer.daniel.database.services.implemantion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.repositories.PersonRepository;
import pwr.glazer.daniel.database.services.PersonServiceInterface;

import java.util.List;


@Service
@Transactional
public class PersonService implements PersonServiceInterface {

    private  final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAllPeople() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public Person getByPersonID(int personID) {
        return personRepository.getByPersonID(personID);
    }

    @Override
    public void saveAllPeople(List<Person> people) {
        personRepository.saveAll(people);
    }

    @Override
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public int getPersonIDByNameAndSurname(String name, String surname) {
        return personRepository.getPersonIDByNameAndSurname(name, surname);
    }
}
