package top.kass.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.PaymentDao;
import top.kass.model.Payment;

import java.sql.Timestamp;

@Repository
public class PaymentDaoImpl implements PaymentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Payment create(int id, int money) {
        Session session = sessionFactory.getCurrentSession();

        Payment payment = new Payment();
        payment.setCustomerId(id);
        payment.setMoney(money);
        payment.setTime(new Timestamp(System.currentTimeMillis()));

        session.save(payment);

        return payment;
    }
}
