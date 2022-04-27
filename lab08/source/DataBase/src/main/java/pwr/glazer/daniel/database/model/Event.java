package pwr.glazer.daniel.database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Setter
@Getter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int eventID;
    private String name;
    private String place;
    private Date date;

    public Event(){}
    public Event(String name, String place, Date date) {
        this.name = name;
        this.place = place;
        this.date = date;
    }
}
