package top.kass.service;

import top.kass.model.Payment;

import java.util.List;

public interface PaymentService {

    public List<Payment> getPaymentByCustomer(int id);

}
