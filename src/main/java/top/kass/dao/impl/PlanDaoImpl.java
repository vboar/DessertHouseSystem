package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.PlanDao;
import top.kass.model.Plan;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class PlanDaoImpl implements PlanDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Date getNextPlanDate(int shopId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Plan where shopId=:shopId");
        query.setInteger("shopId", shopId);

        Date start;

        // 还没有任何产品计划
        if (query.list() == null || query.list().size() == 0) {
            start = new Date();
        } else {
            List<Plan> planList = (List<Plan>)query.list();
            Plan temp = planList.get(planList.size()-1);
            start = temp.getStartTime();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.WEEK_OF_MONTH, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date temp = cal.getTime();
        return temp;
    }
}
