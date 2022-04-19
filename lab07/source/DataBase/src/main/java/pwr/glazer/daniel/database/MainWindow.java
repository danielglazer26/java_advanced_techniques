package pwr.glazer.daniel.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import pwr.glazer.daniel.database.file.ReadFromFile;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;
import pwr.glazer.daniel.database.services.implemantion.EventService;
import pwr.glazer.daniel.database.services.implemantion.PaymentService;
import pwr.glazer.daniel.database.services.implemantion.PersonService;
import pwr.glazer.daniel.database.services.implemantion.RepaymentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MainWindow extends JFrame {

    private ArrayList<DefaultTableModel> defaultTableModelArrayList;
    private final EventService eventService;
    private final PersonService personService;
    private final PaymentService paymentService;
    private final RepaymentService repaymentService;
    private JPanel panel1;
    private JPanel mainPanel;
    private JTable eventTable;
    private JTable personTable;
    private JTable paymentTable;
    private JTable repaymentTable;
    private JButton refreshButton;
    private JComboBox serviceBox;
    private JButton addButton;
    private JButton loadButton;
    private JTextField textToTable;
    private JLabel dateLabel;
    private JButton nextDay;
    private Date applicationDate;
    private JTextArea logsField;

    @Autowired
    public MainWindow(EventService eventService, PersonService personService, PaymentService paymentService, RepaymentService repaymentService) {
        this.eventService = eventService;
        this.personService = personService;
        this.paymentService = paymentService;
        this.repaymentService = repaymentService;

        applicationDate = Date.valueOf(LocalDate.now());
        dateLabel.setText(applicationDate.toString());

        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createTables();

        ReadFromFile.startFileChooser(
                0,
                "C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab07\\source\\DataBase\\wyd.csv",
                eventService);
        ReadFromFile.startFileChooser(
                1,
                "C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab07\\source\\DataBase\\osb.csv",
                personService);
        ReadFromFile.startFileChooser(
                2,
                "C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab07\\source\\DataBase\\raty.csv",
                repaymentService,
                eventService);
        ReadFromFile.startFileChooser(
                3,
                "C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab07\\source\\DataBase\\wplata.csv",
                paymentService,
                personService,
                eventService,
                repaymentService);

        createRefreshButton();
        createAddButton(eventService, personService, paymentService, repaymentService);
        createLoadButton(eventService, personService, paymentService, repaymentService);

        nextDay.addActionListener(e -> {
            updateDate();
            remindAboutMoney();
            checkNoPay();
        });
        pack();
    }

    private void remindAboutMoney() {
    }

    private void checkNoPay() {
        List<Person> people = personService.findAllPeople();
        repaymentService.getByPaymentTimeBefore(applicationDate).forEach(repayment -> {
            people.forEach(person -> {
                if(!paymentService.existsByRepaymentAndAndPersonID(repayment, person))
                    logsField.setText(logsField.getText()
                            + "Log from: "
                            + applicationDate.toString()
                            + ": " + person.getName()
                            + " " + person.getSurname()
                            + " " + repayment.getEvent().getName()
                            + " " + repayment.getValue()
                            + " Payment time: " + repayment.getPaymentTime().toString()
                            + "\n");
            });
        });
    }

    private void updateDate() {
        applicationDate = Date.valueOf(applicationDate.toLocalDate().plusDays(1));
        dateLabel.setText(applicationDate.toString());
    }

    private void createRefreshButton() {
        refreshButton.addActionListener(e -> refreshTables());
    }

    private void createAddButton(EventService eventService,
                                 PersonService personService,
                                 PaymentService paymentService,
                                 RepaymentService repaymentService) {
        addButton.addActionListener(e -> {
            String[] strings = textToTable.getText().split(";");
            switch (serviceBox.getSelectedIndex()) {
                case 0:
                    eventService.saveEvent(new Event(strings[0], strings[1],
                            Date.valueOf(LocalDate.parse(strings[2], DateTimeFormatter.ofPattern("dd.MM.yyyy")))));
                    break;
                case 1:
                    personService.savePerson(new Person(strings[0], strings[1]));
                    break;
                case 2:
                    paymentService.savePayment(new Payment(
                            Date.valueOf(LocalDate.parse(strings[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
                            Double.parseDouble(strings[1]),
                            personService.getByPersonID(Integer.parseInt(strings[2])),
                            eventService.getByEventID(Integer.parseInt(strings[3])),
                            repaymentService.getByRepaymentID(Integer.parseInt(strings[4]))));
                    break;

                case 3:
                    repaymentService.saveRepayment(new Repayment(
                            eventService.getByEventID(Integer.parseInt(strings[0])),
                            Integer.parseInt(strings[1]),
                            Date.valueOf(LocalDate.parse(strings[2], DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
                            Double.parseDouble(strings[3])));
                    break;

            }
            refreshTables();
        });
    }

    private void createLoadButton(EventService eventService,
                                  PersonService personService,
                                  PaymentService paymentService,
                                  RepaymentService repaymentService) {
        loadButton.addActionListener(e -> {
            switch (serviceBox.getSelectedIndex()) {
                case 0:
                    ReadFromFile.startFileChooser(0, eventService);
                    break;
                case 1:
                    ReadFromFile.startFileChooser(1, personService);
                    break;
                case 2:
                    ReadFromFile.startFileChooser(2, repaymentService, eventService);
                    break;
                case 3:
                    ReadFromFile.startFileChooser(3, paymentService, personService, eventService, repaymentService);
                    break;
            }
            refreshTables();
        });
    }

    private void createTables() {
        defaultTableModelArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            defaultTableModelArrayList.add(new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        }
        createEventTable();
        createPersonTable();
        createPaymentTable();
        createRepaymentTable();
    }

    private void refreshTables() {
        defaultTableModelArrayList.forEach(tDefaultTableModel -> tDefaultTableModel.setRowCount(0));
        eventService.findAllEvents().forEach(e -> defaultTableModelArrayList.get(0)
                .addRow(new Object[]{
                        e.getEventID(),
                        e.getName(),
                        e.getPlace(),
                        e.getDate().toString()}));
        personService.findAllPeople().forEach(e -> defaultTableModelArrayList.get(1)
                .addRow(new Object[]{
                        e.getPersonID(),
                        e.getName(),
                        e.getSurname()}));
        repaymentService.findAllRepayments().forEach(e -> defaultTableModelArrayList.get(2)
                .addRow(new Object[]{
                        e.getRepaymentID(),
                        e.getEvent().getEventID(),
                        e.getNumber(),
                        e.getPaymentTime(),
                        e.getValue()}));
        paymentService.findAllPayments().forEach(e -> defaultTableModelArrayList.get(3)
                .addRow(new Object[]{
                        e.getPaymentID(),
                        e.getPaymentDate(),
                        e.getCashValue(),
                        e.getPersonID().getPersonID(),
                        e.getEventID().getEventID(),
                        e.getRepayment().getRepaymentID()}));
        pack();
    }

    private void createEventTable() {
        defaultTableModelArrayList.get(0).addColumn("Event ID");
        defaultTableModelArrayList.get(0).addColumn("Name");
        defaultTableModelArrayList.get(0).addColumn("Place");
        defaultTableModelArrayList.get(0).addColumn("Date");
        eventTable.setModel(defaultTableModelArrayList.get(0));
    }

    private void createPersonTable() {
        defaultTableModelArrayList.get(1).addColumn("Person ID");
        defaultTableModelArrayList.get(1).addColumn("Name");
        defaultTableModelArrayList.get(1).addColumn("Surname");
        personTable.setModel(defaultTableModelArrayList.get(1));
    }

    private void createRepaymentTable() {
        defaultTableModelArrayList.get(2).addColumn("Repayment ID");
        defaultTableModelArrayList.get(2).addColumn("Event ID");
        defaultTableModelArrayList.get(2).addColumn("Number");
        defaultTableModelArrayList.get(2).addColumn("Payment Time");
        defaultTableModelArrayList.get(2).addColumn("Value");
        repaymentTable.setModel(defaultTableModelArrayList.get(2));
    }

    private void createPaymentTable() {
        defaultTableModelArrayList.get(3).addColumn("Payment ID");
        defaultTableModelArrayList.get(3).addColumn("Payment Date");
        defaultTableModelArrayList.get(3).addColumn("Cash Value");
        defaultTableModelArrayList.get(3).addColumn("Person ID");
        defaultTableModelArrayList.get(3).addColumn("Event ID");
        defaultTableModelArrayList.get(3).addColumn("Repayment ID");
        paymentTable.setModel(defaultTableModelArrayList.get(3));
    }


    public static void main(String[] args) {
        var ctx = new SpringApplicationBuilder(MainWindow.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {

            var ex = ctx.getBean(MainWindow.class);
            ex.setVisible(true);
        });
    }

}
