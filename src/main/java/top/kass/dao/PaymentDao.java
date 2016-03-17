package top.kass.dao;

import top.kass.model.Payment;

import java.util.List;

public interface PaymentDao {

    public Payment create(int id, int money);

    public List<Payment> findByCustomerId(int id);

}
