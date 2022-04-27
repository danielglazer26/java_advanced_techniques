package pwr.glazer.daniel.database.services;

import pwr.glazer.daniel.database.model.Event;

import java.sql.Date;
import java.util.List;

public interface EventServiceInterface {
    List<Event> findAllEvents();
    Event getByEventID(int eventID);
    int getEventIDByNameAndPlaceAndDate(String name, String place, Date date);
    void saveAllEvents(List<Event> events);
    void saveEvent(Event event);
}
