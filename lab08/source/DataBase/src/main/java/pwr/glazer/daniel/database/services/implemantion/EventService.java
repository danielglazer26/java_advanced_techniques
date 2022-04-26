package pwr.glazer.daniel.database.services.implemantion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.repositories.EventRepository;
import pwr.glazer.daniel.database.services.EventServiceInterface;

import java.util.List;

@Service
@Transactional
@Component
public class EventService implements EventServiceInterface {


    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> findAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public void saveAllEvents(List<Event> events) {
        eventRepository.saveAll(events);
    }

    @Override
    public Event getByEventID(int eventID) {
        return eventRepository.getByEventID(eventID);
    }

    @Override
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }
}
