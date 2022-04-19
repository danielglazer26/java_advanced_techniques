package pwr.glazer.daniel.database.file;

import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;
import pwr.glazer.daniel.database.services.implemantion.EventService;
import pwr.glazer.daniel.database.services.implemantion.PaymentService;
import pwr.glazer.daniel.database.services.implemantion.PersonService;
import pwr.glazer.daniel.database.services.implemantion.RepaymentService;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class ReadFromFile {

    @SafeVarargs
    public static <T> void startFileChooser(int fileType, T... services) {
        JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        fileChooser.setFileSystemView(FileSystemView.getFileSystemView());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(null) == 0) {
            readFile(fileType, fileChooser.getSelectedFile().toString(), services);
        }
    }
    @SafeVarargs
    public static <T> void startFileChooser(int fileType, String filePath, T... services) {
        readFile(fileType, filePath, services);
    }


    @SafeVarargs
    private static <T> void readFile(int fileType, String filePath, T... services) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));

            br.readLine();

            ArrayList<String[]> arrayList = new ArrayList<>();

            br.lines().forEach(s -> arrayList.add(s.split(";")));

            addDataToBase(fileType, arrayList, services);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(br).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SafeVarargs
    private static <T> void addDataToBase(int fileType, ArrayList<String[]> arrayList, T... services) {

        switch (fileType) {
            case 0:
                ArrayList<Event> data = new ArrayList<>();
                arrayList.forEach(strings -> data.add(new Event(strings[0], strings[1],
                        Date.valueOf(LocalDate.parse(strings[2], DateTimeFormatter.ofPattern("dd.MM.yyyy"))))));
                ((EventService) services[0]).saveAllEvents(data);
                break;
            case 1:
                ArrayList<Person> data1 = new ArrayList<>();
                arrayList.forEach(strings -> data1.add(new Person(strings[0], strings[1])));
                ((PersonService) services[0]).saveAllPeople(data1);
                break;
            case 2:
                ArrayList<Repayment> data2 = new ArrayList<>();
                EventService eventService = (EventService) services[1];

                arrayList.forEach(strings -> data2.add(new Repayment(
                        eventService.getByEventID(Integer.parseInt(strings[0])),
                        Integer.parseInt(strings[1]),
                        Date.valueOf(LocalDate.parse(strings[2], DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
                        Double.parseDouble(strings[3]))));
                ((RepaymentService) services[0]).saveAllRepayments(data2);
                break;

            case 3:
                ArrayList<Payment> data3 = new ArrayList<>();

                PersonService personService = (PersonService) services[1];
                eventService = (EventService) services[2];
                RepaymentService repaymentService = (RepaymentService) services[3];

                arrayList.forEach(strings -> data3.add(new Payment(
                        Date.valueOf(LocalDate.parse(strings[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
                        Double.parseDouble(strings[1]),
                        personService.getByPersonID(Integer.parseInt(strings[2])),
                        eventService.getByEventID(Integer.parseInt(strings[3])),
                        repaymentService.getByRepaymentID(Integer.parseInt(strings[4])))));
                ((PaymentService) services[0]).saveAllPayments(data3);
                break;

        }

    }


}
