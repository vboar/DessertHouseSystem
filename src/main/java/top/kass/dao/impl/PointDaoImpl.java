package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.PointDao;
import top.kass.model.Consumption;
import top.kass.model.Point;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PointDaoImpl implements PointDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Point create(int id, int point, int type, Integer consumptionId) {
        Session session = sessionFactory.getCurrentSession();

        Point temp = new Point();
        temp.setCustomerId(id);
        temp.setPoint(point);
        temp.setType((byte) type);
        if (consumptionId  != null) {
            Consumption consumption = new Consumption();
            consumption.setId(consumptionId);
            temp.setConsumption(consumption);
        }
        temp.setTime(new Timestamp(System.currentTimeMillis()));

        session.save(temp);
        session.flush();
        return temp;
    }

    @Override
    public List<Point> findByCustomerId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Point where customerId=:id order by time desc");
        query.setInteger("id", id);
        return query.list();
    }

}
