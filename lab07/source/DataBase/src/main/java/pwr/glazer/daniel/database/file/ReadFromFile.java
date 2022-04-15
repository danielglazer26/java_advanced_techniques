package pwr.glazer.daniel.database.file;

import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;
import pwr.glazer.daniel.database.repositories.EventRepository;
import pwr.glazer.daniel.database.repositories.PaymentRepository;
import pwr.glazer.daniel.database.repositories.PersonRepository;
import pwr.glazer.daniel.database.repositories.RepaymentRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class ReadFromFile {

    /*public static <T> void startFileChooser(int fileType, T repository) {
     *//*JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        fileChooser.setFileSystemView(FileSystemView.getFileSystemView());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);*//*

     *//*if (fileChooser.showOpenDialog(null) == 0) {
            readFile(fileType, repository, fileChooser.getSelectedFile().toString());
        }*//*
        readFile(fileType, repository, );
    }*/
    @SafeVarargs
    public static <T> void startFileChooser(int fileType, String filePath, T... repository) {
        readFile(fileType, filePath, repository);
    }


    @SafeVarargs
    private static <T> void readFile(int fileType, String filePath, T... repository) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));

            br.readLine();

            ArrayList<String[]> arrayList = new ArrayList<>();

            br.lines().forEach(s -> arrayList.add(s.split(";")));

            addDataToBase(fileType, arrayList, repository);

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
    private static <T> void addDataToBase(int fileType, ArrayList<String[]> arrayList, T... repository) {


        switch (fileType) {
            case 0:
                ArrayList<Event> data = new ArrayList<>();
                arrayList.forEach(strings -> data.add(new Event(strings[0], strings[1],
                        Date.valueOf(LocalDate.parse(strings[2], DateTimeFormatter.ofPattern("dd.MM.yyyy"))))));
                ((EventRepository) repository[0]).saveAll(data);
                break;
            case 1:
                ArrayList<Person> data1 = new ArrayList<>();
                arrayList.forEach(strings -> data1.add(new Person(strings[0], strings[1])));
                ((PersonRepository) repository[0]).saveAll(data1);
                break;
            case 2:
                ArrayList<Repayment> data2 = new ArrayList<>();
                EventRepository eventRepository = (EventRepository) repository[1];
                arrayList.forEach(strings -> data2.add(new Repayment(
                        eventRepository.getByEventID(Integer.parseInt(strings[0])),
                        Integer.parseInt(strings[1]),
                        Date.valueOf(LocalDate.parse(strings[2], DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
                        Double.parseDouble(strings[3]))));
                ((RepaymentRepository) repository[0]).saveAll(data2);
                break;

            case 3:
                ArrayList<Payment> data3 = new ArrayList<>();

                PersonRepository personRepository = (PersonRepository) repository[1];
                eventRepository = (EventRepository) repository[2];
                RepaymentRepository repaymentRepository = (RepaymentRepository) repository[3];

                arrayList.forEach(strings -> data3.add(new Payment(
                        Date.valueOf(LocalDate.parse(strings[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
                        Double.parseDouble(strings[1]),
                        personRepository.getByPersonID(Integer.parseInt(strings[2])),
                        eventRepository.getByEventID(Integer.parseInt(strings[3])),
                        repaymentRepository.getByRepaymentID(Integer.parseInt(strings[4])))));
                ((PaymentRepository) repository[0]).saveAll(data3);
                break;

        }

    }


}
