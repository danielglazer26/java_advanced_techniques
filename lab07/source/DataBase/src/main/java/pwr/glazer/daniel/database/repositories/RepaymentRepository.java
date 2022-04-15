package pwr.glazer.daniel.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pwr.glazer.daniel.database.model.Repayment;


public interface RepaymentRepository extends CrudRepository<Repayment,Integer>{
    Repayment getByRepaymentID(int repaymentID);
}

