package pwr.glazer.daniel.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pwr.glazer.daniel.database.model.Event;

import java.sql.Date;

@Repository
public interface EventRepository extends CrudRepository<Event,Integer>{
    Event getByEventID(int eventID);

    @Query("select e.eventID from Event e where e.name = ?1 and e.place = ?2 and e.date = ?3")
    int getEventIDByNameAndPlaceAndDate(String name, String place, Date date);
}

