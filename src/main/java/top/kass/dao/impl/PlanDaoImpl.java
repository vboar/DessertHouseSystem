package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.PlanDao;
import top.kass.model.Plan;
import top.kass.model.PlanItem;
import top.kass.model.Product;
import top.kass.model.Shop;

import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public void create(String data, int shopId) {
        Session session = sessionFactory.getCurrentSession();

        JSONObject object = new JSONObject(data);

        String startDate = (String)object.get("startDate") + " 00:00:00";
        String endDate = (String)object.get("endDate") + " 23:59:59";
        Timestamp startTime = Timestamp.valueOf(startDate);
        Timestamp endTime = Timestamp.valueOf(endDate);

        Plan plan = new Plan();
        plan.setStartTime(startTime);
        plan.setEndTime(endTime);
        plan.setShopId(shopId);

        JSONArray items = (JSONArray)object.get("items");

        plan.setPlanItems(getPlanItems(items));

        session.save(plan);
        session.flush();

    }

    @Override
    public Plan findById(int planId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Plan where id=:id");
        query.setInteger("id", planId);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Plan)query.list().get(0);
        }
    }

    @Override
    public void update(String data) {
        Session session = sessionFactory.getCurrentSession();

        JSONObject object = new JSONObject(data);

        int planId = (int)object.get("planId");

        Plan plan = findById(planId);

        JSONArray items = (JSONArray)object.get("items");
        plan.setPlanItems(getPlanItems(items));

        session.save(plan);
        session.flush();

    }

    private Set<PlanItem> getPlanItems(JSONArray items) {
        Set<PlanItem> planItemSet = new HashSet<>();

        for (int i = 0; i < items.length(); i++) {

            JSONObject temp = (JSONObject) items.get(i);
            int productId = (int)temp.get("productId");
            double price = (double)temp.get("price");
            int number = (int)temp.get("number");
            int point = (int)temp.get("point");

            String dateString = (String)temp.get("date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tempD = null;
            try {
                tempD = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Date date = new java.sql.Date(tempD.getTime());

            PlanItem planItem = new PlanItem();
            Product product = new Product();
            product.setId(productId);
            planItem.setProduct(product);
            planItem.setPrice(price);
            planItem.setNumber(number);
            planItem.setRemaining(number);
            planItem.setPoint(point);
            planItem.setDate(date);

            planItemSet.add(planItem);
        }
        return planItemSet;
    }


}
