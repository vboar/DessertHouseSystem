package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.Index;
import org.hibernate.transform.Transformers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.PlanDao;
import top.kass.model.Plan;
import top.kass.model.PlanItem;
import top.kass.model.Product;
import top.kass.vo.IndexProduct;

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
        session.save(plan);

        JSONArray items = (JSONArray)object.get("items");

        plan.setPlanItems(getPlanItems(items, plan));

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

        Query query = session.createSQLQuery("DELETE FROM plan_item WHERE plan_id=?");
        query.setParameter(0, plan.getId());
        query.executeUpdate();

        JSONArray items = (JSONArray)object.get("items");
        plan.setPlanItems(getPlanItems(items, plan));
        plan.setStatus((byte)0);

        session.save(plan);
        session.flush();

    }

    @Override
    public void updateStatus(Plan plan) {
        Session session = sessionFactory.getCurrentSession();
        session.save(plan);
        session.flush();
    }

    @Override
    public List<Plan> getPlansByShop(int shopId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Plan where shopId=:shopId order by startTime desc");
        query.setInteger("shopId", shopId);
        List<Plan> planList = query.list();
        return planList;
    }

    @Override
    public List<Plan> getPlansByStatus(int status) {
        Session session = sessionFactory.getCurrentSession();
        Query query;
        if (status == 0) {
            query = session.createQuery("from Plan where status=0 order by startTime desc");
        } else {
            query = session.createQuery("from Plan where status<>0 order by startTime desc");
        }
        List<Plan> planList = query.list();
        return planList;
    }

    @Override
    public PlanItem findByProductAndDate(int productId, String date) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from PlanItem where " +
                "product=:productId and date=:date");
        query.setInteger("productId", productId);
        query.setString("date", date);
        return (PlanItem) query.list().get(0);
    }

    @Override
    public List<IndexProduct> findByShopAndSearch(int shopId, String key) {
        Session session = sessionFactory.getCurrentSession();
        Query query;

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String current = sdf.format(date);

        List list;
        List<IndexProduct> products = new ArrayList<>();

        if (key == null) {

            query = session.createSQLQuery("SELECT plan_item.product_id AS id, " +
                    "product.name AS name, " +
                    "product.img AS imgPath, " +
                    "MIN(plan_item.price) AS minPrice " +
                    "FROM plan_item LEFT JOIN plan ON plan_item.plan_id = plan.id " +
                    "LEFT JOIN product ON plan_item.product_id = product.id " +
                    "WHERE plan.shop_id=? AND date>=? GROUP BY plan_item.product_id");
            query.setParameter(0, shopId);
            query.setParameter(1, current);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            list = query.list();

        } else {

            query = session.createSQLQuery("SELECT plan_item.product_id AS id, " +
                    "product.name AS name, " +
                    "product.img AS imgPath, " +
                    "MIN(plan_item.price) AS minPrice " +
                    "FROM plan_item LEFT JOIN plan ON plan_item.plan_id = plan.id " +
                    "LEFT JOIN product ON plan_item.product_id = product.id " +
                    "WHERE plan.shop_id=? AND date>=? AND product.name LIKE '%"
                    + key + "%'GROUP BY plan_item.product_id");
            query.setParameter(0, shopId);
            query.setParameter(1, current);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            list = query.list();

        }

        for (int i = 0; i < list.size(); i++) {
            Map map = (Map)list.get(i);
            IndexProduct product = new IndexProduct();
            product.setId((int)map.get("id"));
            product.setName((String)map.get("name"));
            product.setImgPath((String)map.get("imgPath"));
            product.setMinPrice((double)map.get("minPrice"));
            products.add(product);
        }

        return products;
    }

    @Override
    public List<PlanItem> getByProduct(int productId) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from PlanItem where product=:productId and date>=current_date()" +
                " and plan.status=1 order by date asc");
        query.setInteger("productId", productId);
        return query.list();
    }

    @Override
    public List<PlanItem> getTodayByShop(int shopId) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from PlanItem where plan.shopId=:shopId and date=current_date()" +
                " and plan.status=1 order by date desc");
        query.setInteger("shopId", shopId);
        return query.list();
    }

    @Override
    public void updatePlanItem(int productId, String date, int number) {
        System.out.println("aaa");
        System.out.println(productId);
        System.out.println(date);
        System.out.println(number);
        Session session = sessionFactory.getCurrentSession();
        Query query  = session.createSQLQuery("UPDATE plan_item " +
                "SET remaining=remaining-? WHERE product_id=? AND date=?");
        query.setInteger(0, number);
        query.setInteger(1, productId);
        query.setString(2, date);
        query.executeUpdate();
        session.flush();
    }

    private Set<PlanItem> getPlanItems(JSONArray items, Plan plan) {

        Set<PlanItem> planItemSet = new HashSet<>();

        for (int i = 0; i < items.length(); i++) {

            JSONObject temp = (JSONObject) items.get(i);
            int productId = Integer.parseInt(temp.get("productId").toString());
            double price = Double.parseDouble(temp.get("price").toString());
            int number = Integer.parseInt(temp.get("number").toString());
            int point = Integer.parseInt(temp.get("point").toString());

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
            planItem.setPlan(plan);
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
