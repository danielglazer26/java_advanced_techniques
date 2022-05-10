package pwr.glazer.daniel.database.services;

import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;

import java.sql.Date;
import java.util.List;

public interface PaymentServiceInterface {
    Payment getPaymentByPaymentID(int paymentID);
    List<Payment> findAllPayments();
    Boolean existsByRepaymentAndPersonID(Repayment repayment, Person personID);
    void saveAllPayments(List<Payment> payments);
    void savePayment(Payment payment);
    int getPaymentIDByPaymentDateAndCashValueAndPersonIDAndEventIDAndRepayment(
            Date paymentDate,
            double cashValue,
            Person personID,
            Event eventID,
            Repayment repayment);
}
