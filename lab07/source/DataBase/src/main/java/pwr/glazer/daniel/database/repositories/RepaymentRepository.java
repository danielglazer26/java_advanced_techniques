package pwr.glazer.daniel.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pwr.glazer.daniel.database.model.Repayment;

import java.sql.Date;
import java.util.List;


public interface RepaymentRepository extends CrudRepository<Repayment,Integer>{
    Repayment getByRepaymentID(int repaymentID);
    List<Repayment> getByPaymentTimeBefore(Date paymentTime);
}

