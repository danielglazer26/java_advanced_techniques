package pwr.glazer.daniel.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pwr.glazer.daniel.database.model.Event;

import java.util.List;


public interface EventRepository extends CrudRepository<Event,Integer>{
    List<Event> getAllBy();
    Event getByEventID(int eventID);
}

