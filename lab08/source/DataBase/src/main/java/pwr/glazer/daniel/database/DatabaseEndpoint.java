package pwr.glazer.daniel.database;


import localhost.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;
import pwr.glazer.daniel.database.services.implemantion.EventService;
import pwr.glazer.daniel.database.services.implemantion.PaymentService;
import pwr.glazer.daniel.database.services.implemantion.PersonService;
import pwr.glazer.daniel.database.services.implemantion.RepaymentService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Endpoint
public class DatabaseEndpoint {
    private static final String NAMESPACE_URI = "localhost";

    private final EventService eventService;
    private final PersonService personService;
    private final RepaymentService repaymentService;
    private final PaymentService paymentService;

    @Autowired
    public DatabaseEndpoint(EventService eventService,
                            PersonService personService,
                            RepaymentService repaymentService,
                            PaymentService paymentService) {
        this.eventService = eventService;
        this.personService = personService;
        this.repaymentService = repaymentService;
        this.paymentService = paymentService;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEventRequest")
    @ResponsePayload
    public GetEventResponse getEventResponse(@RequestPayload GetEventRequest request) {
        GetEventResponse response = new GetEventResponse();

        Event event = eventService.getByEventID(request.getEventID());
        response.setEvent(convertEventToSoapEvent(event));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setEventRequest")
    @ResponsePayload
    public SetEventResponse setEventResponse(@RequestPayload SetEventRequest request) {
        SetEventResponse response = new SetEventResponse();

        Date date = convertStringToDate(request.getDate());
        eventService.saveEvent(new Event(request.getName(), request.getPlace(), date));
        response.setEventID(eventService.getEventIDByNameAndPlaceAndDate(request.getName(), request.getPlace(), date));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonRequest")
    @ResponsePayload
    public GetPersonResponse getPersonResponse(@RequestPayload GetPersonRequest request) {
        GetPersonResponse response = new GetPersonResponse();

        Person person = personService.getByPersonID(request.getPersonID());
        response.setPerson(convertPersonToSoapPerson(person));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setPersonRequest")
    @ResponsePayload
    public SetPersonResponse setPersonResponse(@RequestPayload SetPersonRequest request) {
        SetPersonResponse response = new SetPersonResponse();

        personService.savePerson(new Person(request.getName(), request.getSurname()));
        response.setPersonID(personService.getPersonIDByNameAndSurname(request.getName(), request.getSurname()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRepaymentRequest")
    @ResponsePayload
    public GetRepaymentResponse getRepaymentResponse(@RequestPayload GetRepaymentRequest request) {
        GetRepaymentResponse response = new GetRepaymentResponse();

        Repayment repayment = repaymentService.getByRepaymentID(request.getRepaymentID());
        response.setRepayment(convertRepaymentToSoapRepayment(repayment));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setRepaymentRequest")
    @ResponsePayload
    public SetRepaymentResponse setRepaymentResponse(@RequestPayload SetRepaymentRequest request) {
        SetRepaymentResponse response = new SetRepaymentResponse();

        Date date = convertStringToDate(request.getPaymentTime());
        Event event = convertSoapEventToEvent(request.getEventID());
        repaymentService.saveRepayment(new Repayment(
                event,
                request.getNumber(),
                date,
                request.getValue()));
        response.setRepaymentID(repaymentService.getRepaymentIDByEventAndNumberAndPaymentTimeAndValue(
                event,
                request.getNumber(),
                date,
                request.getValue()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPaymentRequest")
    @ResponsePayload
    public GetPaymentResponse getPaymentResponse(@RequestPayload GetPaymentRequest request) {
        GetPaymentResponse response = new GetPaymentResponse();

        Payment payment = paymentService.getPaymentByPaymentID(request.getPaymentID());
        response.setPayment(convertPaymentToSoapPayment(payment));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setPaymentRequest")
    @ResponsePayload
    public SetPaymentResponse setPaymentResponse(@RequestPayload SetPaymentRequest request) {
        SetPaymentResponse response = new SetPaymentResponse();

        Event event = convertSoapEventToEvent(request.getEventID());
        Person person = convertSoapPersonToPerson(request.getPersonID());
        Repayment repayment = convertSoapRepaymentToRepayment(request.getRepaymentID());
        paymentService.savePayment(new Payment(
                convertStringToDate(request.getPaymentDate()),
                request.getCashValue(),
                person,
                event,
                repayment));
        response.setPaymentID(paymentService.getPaymentIDByPaymentDateAndCashValueAndPersonIDAndEventIDAndRepayment(
                convertStringToDate(request.getPaymentDate()),
                request.getCashValue(),
                person,
                event,
                repayment
        ));

        return response;
    }

    private static Date convertStringToDate(String date) {
        return Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    private static localhost.Event convertEventToSoapEvent(Event event) {
        localhost.Event eventSoap = new localhost.Event();
        eventSoap.setEventID(event.getEventID());
        eventSoap.setName(event.getName());
        eventSoap.setPlace(event.getPlace());
        eventSoap.setDate(event.getDate().toString());
        return eventSoap;
    }

    private static Event convertSoapEventToEvent(localhost.Event eventSoap) {
        Event event = new Event();
        event.setEventID(eventSoap.getEventID());
        event.setName(eventSoap.getName());
        event.setPlace(eventSoap.getPlace());
        event.setDate(convertStringToDate(eventSoap.getDate()));
        return event;
    }

    private static localhost.Person convertPersonToSoapPerson(Person person) {
        localhost.Person personSoap = new localhost.Person();
        personSoap.setPersonID(person.getPersonID());
        personSoap.setName(person.getName());
        personSoap.setSurname(person.getSurname());
        return personSoap;
    }

    private static Person convertSoapPersonToPerson(localhost.Person personSoap) {
        Person person = new Person();
        person.setPersonID(personSoap.getPersonID());
        person.setName(personSoap.getName());
        person.setSurname(personSoap.getSurname());
        return person;
    }

    private static localhost.Repayment convertRepaymentToSoapRepayment(Repayment repayment) {
        localhost.Repayment repaymentSoap = new localhost.Repayment();
        repaymentSoap.setRepaymentID(repayment.getRepaymentID());
        repaymentSoap.setEventID(convertEventToSoapEvent(repayment.getEvent()));
        repaymentSoap.setNumber(repayment.getNumber());
        repaymentSoap.setValue(repayment.getValue());
        repaymentSoap.setPaymentTime(repayment.getPaymentTime().toString());
        return repaymentSoap;
    }

    private static Repayment convertSoapRepaymentToRepayment(localhost.Repayment repaymentSoap) {
        Repayment repayment = new Repayment();
        repayment.setRepaymentID(repaymentSoap.getRepaymentID());
        repayment.setEvent(convertSoapEventToEvent(repaymentSoap.getEventID()));
        repayment.setNumber(repaymentSoap.getNumber());
        repayment.setValue(repaymentSoap.getValue());
        repayment.setPaymentTime(convertStringToDate(repaymentSoap.getPaymentTime()));
        return repayment;
    }

    private localhost.Payment convertPaymentToSoapPayment(Payment payment) {
        localhost.Payment paymentSoap = new localhost.Payment();
        paymentSoap.setPaymentID(payment.getPaymentID());
        paymentSoap.setPaymentDate(payment.getPaymentDate().toString());
        paymentSoap.setEventID(convertEventToSoapEvent(payment.getEventID()));
        paymentSoap.setPersonID(convertPersonToSoapPerson(payment.getPersonID()));
        paymentSoap.setRepaymentID(convertRepaymentToSoapRepayment(payment.getRepayment()));
        paymentSoap.setCashValue(payment.getCashValue());
        return paymentSoap;
    }
}
