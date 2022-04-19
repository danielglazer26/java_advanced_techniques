package pwr.glazer.daniel.database.services.implemantion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.glazer.daniel.database.model.Repayment;
import pwr.glazer.daniel.database.repositories.RepaymentRepository;
import pwr.glazer.daniel.database.services.RepaymentServiceInterface;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class RepaymentService implements RepaymentServiceInterface {

    private final RepaymentRepository repaymentRepository;

    @Autowired
    public RepaymentService(RepaymentRepository repaymentRepository) {
        this.repaymentRepository = repaymentRepository;
    }

    @Override
    public List<Repayment> findAllRepayments() {
        return (List<Repayment>) repaymentRepository.findAll();
    }

    @Override
    public List<Repayment> getByPaymentTimeBefore(Date paymentTime) {
        return repaymentRepository.getByPaymentTimeBefore(paymentTime);
    }

    @Override
    public Repayment getByRepaymentID(int repaymentID) {
        return repaymentRepository.getByRepaymentID(repaymentID);
    }

    @Override
    public void saveAllRepayments(List<Repayment> repayments) {
            repaymentRepository.saveAll(repayments);
    }

    @Override
    public void saveRepayment(Repayment repayment) {
        repaymentRepository.save(repayment);
    }
}
