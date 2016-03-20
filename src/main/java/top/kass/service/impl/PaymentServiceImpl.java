package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.PaymentDao;
import top.kass.model.Payment;
import top.kass.service.PaymentService;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public List<Payment> getPaymentByCustomer(int id) {
        return paymentDao.findByCustomerId(id);
    }

}
