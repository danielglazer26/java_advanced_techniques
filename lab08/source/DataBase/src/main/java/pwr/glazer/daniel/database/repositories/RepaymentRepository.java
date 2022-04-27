package pwr.glazer.daniel.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Repayment;

import java.sql.Date;
import java.util.List;

@Repository
public interface RepaymentRepository extends CrudRepository<Repayment,Integer>{
    Repayment getByRepaymentID(int repaymentID);
    List<Repayment> getByPaymentTimeBefore(Date paymentTime);
    List<Repayment> getAllByPaymentTimeEquals(Date paymentTime);
    @Query("select r.repaymentID from Repayment r where r.event = ?1 and r.number = ?2 and r.paymentTime = ?3 and r.value = ?4")
    int getRepaymentIDByEventAndNumberAndPaymentTimeAndValue(Event event, int number, Date paymentTime, Double value);
}

