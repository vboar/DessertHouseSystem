package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.PaymentDao;
import top.kass.model.Payment;

import java.sql.Timestamp;
import java.util.List;

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

    @Override
    public List<Payment> findByCustomerId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Payment where customerId=:id order by time desc");
        query.setInteger("id", id);
        return query.list();
    }
}
