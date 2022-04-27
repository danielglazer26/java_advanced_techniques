package pwr.glazer.daniel.database.services.implemantion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.glazer.daniel.database.model.Payment;
import pwr.glazer.daniel.database.model.Person;
import pwr.glazer.daniel.database.model.Repayment;
import pwr.glazer.daniel.database.repositories.PaymentRepository;
import pwr.glazer.daniel.database.services.PaymentServiceInterface;

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
    public List<Payment> findAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    @Override
    public Boolean existsByRepaymentAndAndPersonID(Repayment repayment, Person personID) {
        return paymentRepository.existsByRepaymentAndAndPersonID(repayment, personID);
    }


    @Override
    public void saveAllPayments(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }

    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
