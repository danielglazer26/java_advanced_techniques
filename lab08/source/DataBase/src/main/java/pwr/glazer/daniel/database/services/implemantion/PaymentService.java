package pwr.glazer.daniel.database.services.implemantion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.glazer.daniel.database.model.Event;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;
import pwr.glazer.daniel.database.repositories.PaymentRepository;
import pwr.glazer.daniel.database.services.PaymentServiceInterface;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class PaymentService implements PaymentServiceInterface {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment getPaymentByPaymentID(int paymentID) {
        return paymentRepository.getPaymentByPaymentID(paymentID);
    }

    @Override
    public List<Payment> findAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    @Override
    public Boolean existsByRepaymentAndPersonID(Repayment repayment, Person personID) {
        return paymentRepository.existsByRepaymentAndPersonID(repayment, personID);
    }

    @Override
    public void saveAllPayments(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }

    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public int getPaymentIDByPaymentDateAndCashValueAndPersonIDAndEventIDAndRepayment(
            Date paymentDate, double cashValue, Person personID, Event eventID, Repayment repayment) {
        return paymentRepository.getPaymentIDByPaymentDateAndCashValueAndPersonIDAndEventIDAndRepayment(
                paymentDate, cashValue, personID, eventID, repayment);
    }
}
