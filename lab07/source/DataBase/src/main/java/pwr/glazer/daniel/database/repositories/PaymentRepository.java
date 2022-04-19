package pwr.glazer.daniel.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;

import java.util.List;


public interface PaymentRepository extends CrudRepository<Payment,Integer>{
    List<Payment> getPaymentsByRepayment(Repayment repayment);
    Boolean existsByRepaymentAndAndPersonID(Repayment repayment, Person personID);
}

