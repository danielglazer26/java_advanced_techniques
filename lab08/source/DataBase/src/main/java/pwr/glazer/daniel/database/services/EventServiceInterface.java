package pwr.glazer.daniel.database.services;

import pwr.glazer.daniel.database.model.Event;

import java.util.List;

public interface EventServiceInterface {
    List<Event> findAllEvents();
    void saveAllEvents(List<Event> events);
    Event getByEventID(int eventID);
    void saveEvent(Event event);
}
