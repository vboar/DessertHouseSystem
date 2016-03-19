package top.kass.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.ConsumptionDao;
import top.kass.model.Consumption;

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
}
