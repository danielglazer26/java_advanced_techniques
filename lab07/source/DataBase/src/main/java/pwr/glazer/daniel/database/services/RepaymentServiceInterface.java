package pwr.glazer.daniel.database.services;

import pwr.glazer.daniel.database.model.Repayment;

import java.sql.Date;
import java.util.List;

public interface RepaymentServiceInterface {
    List<Repayment> findAllRepayments();
    List<Repayment> getByPaymentTimeBefore(Date paymentTime);
    Repayment getByRepaymentID(int repaymentID);
    void saveAllRepayments(List<Repayment> repayments);
    void saveRepayment(Repayment repayment);
}
