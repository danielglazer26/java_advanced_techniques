package pwr.glazer.daniel.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;

import java.sql.Date;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    Boolean existsByRepaymentAndPersonID(Repayment repayment, Person personID);

    Payment getPaymentByPaymentID(int paymentID);

    @Query("select p.paymentID from Payment p " +
            "where p.paymentDate = ?1 and p.cashValue = ?2 and p.personID = ?3 and p.eventID = ?4 and p.repayment = ?5")
    int getPaymentIDByPaymentDateAndCashValueAndPersonIDAndEventIDAndRepayment(
            Date paymentDate,
            double cashValue,
            Person personID,
            Event eventID,
            Repayment repayment);
}

