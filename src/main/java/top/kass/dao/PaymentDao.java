package top.kass.dao;

import top.kass.model.Payment;

public interface PaymentDao {

    public Payment create(int id, int money);

}
