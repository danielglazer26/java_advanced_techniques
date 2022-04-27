package pwr.glazer.daniel.database;

import io.spring.guides.gs_producing_web_service.GetEventRequest;
import io.spring.guides.gs_producing_web_service.GetEventResponse;
import io.spring.guides.gs_producing_web_service.GetPersonRequest;
import io.spring.guides.gs_producing_web_service.GetPersonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.services.implemantion.EventService;
import pwr.glazer.daniel.database.services.implemantion.PersonService;

@Endpoint
public class EventEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final EventService eventService;
    private final PersonService personService;

    @Autowired
    public EventEndpoint(EventService eventService, PersonService personService) {
        this.eventService = eventService;
        this.personService = personService;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEventRequest")
    @ResponsePayload
    public GetEventResponse getEventResponse(@RequestPayload GetEventRequest request) {
        GetEventResponse response = new GetEventResponse();
        Event event = eventService.getByEventID(request.getEventID());

        io.spring.guides.gs_producing_web_service.Event e = new io.spring.guides.gs_producing_web_service.Event();
        e.setEventID(event.getEventID());
        e.setName(event.getName());
        e.setPlace(event.getPlace());
        e.setDate(event.getDate().toString());


        response.setEvent(e);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonRequest")
    @ResponsePayload
    public GetPersonResponse getPersonResponse(@RequestPayload GetPersonRequest request) {
        GetPersonResponse response = new GetPersonResponse();
        int personID = request.getPersonID();
        Person person = personService.getByPersonID(personID);

        io.spring.guides.gs_producing_web_service.Person p = new io.spring.guides.gs_producing_web_service.Person();

        p.setPersonID(personID);
        p.setName(person.getName());
        p.setSurname(person.getSurname());

        response.setPerson(p);

        return response;
    }


}
