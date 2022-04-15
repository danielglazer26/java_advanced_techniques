package pwr.glazer.daniel.database.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Getter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentID;
    private Date paymentDate;
    private double cashValue;
    @OneToOne
    @JoinColumn(name = "person_id_person_id")
    private Person personID;
    @OneToOne
    @JoinColumn
    private Event eventID;
    @OneToOne
    @JoinColumn(name = "repaymentID")
    private Repayment repayment;

    public Payment() {
    }

    public Payment(Date paymentDate, double cashValue, Person personID, Event eventID, Repayment repayment) {
        this.paymentDate = paymentDate;
        this.cashValue = cashValue;
        this.personID = personID;
        this.eventID = eventID;
        this.repayment = repayment;
    }
}
