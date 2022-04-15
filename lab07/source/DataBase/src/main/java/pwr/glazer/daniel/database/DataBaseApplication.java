package pwr.glazer.daniel.database;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pwr.glazer.daniel.database.file.ReadFromFile;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.repositories.EventRepository;
import pwr.glazer.daniel.database.repositories.PaymentRepository;
import pwr.glazer.daniel.database.repositories.PersonRepository;
import pwr.glazer.daniel.database.repositories.RepaymentRepository;

import java.util.List;

@SpringBootApplication
public class DataBaseApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataBaseApplication.class);
    private final PaymentRepository paymentRepository;
    private final RepaymentRepository repaymentRepository;
    private final EventRepository eventRepository;
    private final PersonRepository personRepository;


    @Autowired
    public DataBaseApplication(PaymentRepository paymentRepository,
                               RepaymentRepository repaymentRepository,
                               EventRepository eventRepository,
                               PersonRepository personRepository) {
        this.paymentRepository = paymentRepository;
        this.repaymentRepository = repaymentRepository;
        this.eventRepository = eventRepository;
        this.personRepository = personRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(DataBaseApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ReadFromFile.startFileChooser(
                0,
                "C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab07\\source\\DataBase\\wyd.csv",
                eventRepository);
        ReadFromFile.startFileChooser(
                1,
                "C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab07\\source\\DataBase\\osb.csv",
                personRepository);
        ReadFromFile.startFileChooser(
                2,
                "C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab07\\source\\DataBase\\raty.csv",
                repaymentRepository,
                eventRepository);
        ReadFromFile.startFileChooser(
                3,
                "C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab07\\source\\DataBase\\wplata.csv",
                paymentRepository,
                personRepository,
                eventRepository,
                repaymentRepository);
        log.info("TEST");
        List<Event> events = (List<Event>) eventRepository.findAll();
        events.forEach(event -> log.info(event.getName()));


    }


}
