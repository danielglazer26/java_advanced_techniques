package pwr.glazer.daniel.database.controller;

import pwr.glazer.daniel.database.services.implemantion.EventService;
import pwr.glazer.daniel.database.services.implemantion.PaymentService;
import pwr.glazer.daniel.database.services.implemantion.PersonService;
import pwr.glazer.daniel.database.services.implemantion.RepaymentService;

public class BaseController {

    private EventService eventService;
    private PersonService personService;
    private PaymentService paymentService;
    private RepaymentService repaymentService;

    public BaseController(EventService eventService, PersonService personService, PaymentService paymentService, RepaymentService repaymentService) {
        this.eventService = eventService;
        this.personService = personService;
        this.paymentService = paymentService;
        this.repaymentService = repaymentService;
    }
    public void menu(){
        while (true){

        }
    }
}
