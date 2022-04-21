package pwr.glazer.daniel.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;


public interface PaymentRepository extends CrudRepository<Payment,Integer>{
    Boolean existsByRepaymentAndAndPersonID(Repayment repayment, Person personID);
}

