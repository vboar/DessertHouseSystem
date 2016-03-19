package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.ConsumptionDao;
import top.kass.model.Consumption;

import java.util.List;

@Repository
public class ConsumptionDaoImpl implements ConsumptionDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Consumption createOrUpdate(Consumption consumption) {
        Session session = sessionFactory.getCurrentSession();
        session.save(consumption);
        session.flush();
        return consumption;
    }

    @Override
    public List<Consumption> getByCustomerId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Consumption where customerId=:id order by time desc");
        query.setInteger("id", id);
        return query.list();
    }
}
