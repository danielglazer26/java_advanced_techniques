package pwr.glazer.daniel.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pwr.glazer.daniel.database.model.Payment;


public interface PaymentRepository extends CrudRepository<Payment,Integer>{
}

