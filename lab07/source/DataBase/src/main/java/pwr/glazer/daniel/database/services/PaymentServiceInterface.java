package pwr.glazer.daniel.database.services;

import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;

import java.util.List;

public interface PaymentServiceInterface {
    List<Payment> findAllPayments();
    List<Payment> getPaymentsByRepayment(Repayment repayment);
    Boolean existsByRepaymentAndAndPersonID(Repayment repayment, Person personID);
    void saveAllPayments(List<Payment> payments);
    void savePayment(Payment payment);
}
