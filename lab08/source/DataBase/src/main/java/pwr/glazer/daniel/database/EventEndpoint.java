package pwr.glazer.daniel.database;

import io.spring.guides.gs_producing_web_service.GetEventRequest;
import io.spring.guides.gs_producing_web_service.GetEventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.services.implemantion.EventService;

@Endpoint
public class EventEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final EventService eventService;

    @Autowired
    public EventEndpoint(EventService eventService) {
        this.eventService = eventService;
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
}
