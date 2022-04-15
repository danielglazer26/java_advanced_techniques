package pwr.glazer.daniel.database.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Getter
@Entity
public class Repayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int repaymentID;
    @OneToOne
    @JoinColumn(name = "event_event_id")
    private Event event;
    private int number;
    private Date paymentTime;
    private Double value;

    public Repayment() {}

    public Repayment(Event event, int number, Date paymentTime, Double value) {
        this.event = event;
        this.number = number;
        this.paymentTime = paymentTime;
        this.value = value;
    }
}
