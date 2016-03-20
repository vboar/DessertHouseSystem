package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.StatisticDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StatisticDaoImpl implements StatisticDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Map<String, Object> getCustomerAge() {
        Session session = sessionFactory.getCurrentSession();
        Map<String, Object> map = new HashMap<>();

        Query query = session.createSQLQuery("SELECT count(*) FROM customer_info " +
                "WHERE year(CURRENT_DATE())-year(birthday)<20");
        map.put("20岁以下", query.list().get(0));

        query = session.createSQLQuery("SELECT count(*) FROM customer_info " +
                "WHERE year(CURRENT_DATE())-year(birthday)>=20 AND " +
                "year(CURRENT_DATE())-year(birthday)<30");
        map.put("20~30岁", query.list().get(0));

        query = session.createSQLQuery("SELECT count(*) FROM customer_info " +
                "WHERE year(CURRENT_DATE())-year(birthday)>=30 AND " +
                "year(CURRENT_DATE())-year(birthday)<40");
        map.put("30~40岁", query.list().get(0));

        query = session.createSQLQuery("SELECT count(*) FROM customer_info " +
                "WHERE year(CURRENT_DATE())-year(birthday)>=40 AND " +
                "year(CURRENT_DATE())-year(birthday)<50");
        map.put("40~50岁", query.list().get(0));

        query = session.createSQLQuery("SELECT count(*) FROM customer_info " +
                "WHERE year(CURRENT_DATE())-year(birthday)>=50 AND " +
                "year(CURRENT_DATE())-year(birthday)<60");
        map.put("50~60岁", query.list().get(0));

        query = session.createSQLQuery("SELECT count(*) FROM customer_info " +
                "WHERE year(CURRENT_DATE())-year(birthday)>=60");
        map.put("60岁以上", query.list().get(0));

        return map;
    }

    @Override
    public Map<String, Object> getCustomerGender() {
        Session session = sessionFactory.getCurrentSession();
        Map<String, Object> map = new HashMap<>();

        Query query = session.createSQLQuery("SELECT gender, count(*) AS num FROM customer_info " +
                "GROUP BY gender");
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        for (int i = 0; i < list.size(); i++) {
            Map temp = (Map)list.get(i);
            if ((byte)temp.get("gender") == 1) {
                map.put("男", temp.get("num"));
            } else {
                map.put("女", temp.get("num"));
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> getCustomerCity() {
        Session session = sessionFactory.getCurrentSession();
        Map<String, Object> map = new HashMap<>();

        Query query = session.createSQLQuery("SELECT city, count(*) AS num FROM customer_info " +
                "GROUP BY city");
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        for (int i = 0; i < list.size(); i++) {
            Map temp = (Map)list.get(i);
            map.put(temp.get("city").toString(), temp.get("num"));
        }
        return map;
    }

    @Override
    public Map<String, Object> getCustomerStatus() {
        Session session = sessionFactory.getCurrentSession();
        Map<String, Object> map = new HashMap<>();

        Query query = session.createSQLQuery("SELECT status, count(*) AS num FROM customer " +
                "GROUP BY status");
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        for (int i = 0; i < list.size(); i++) {
            Map temp = (Map)list.get(i);
            switch ((byte)temp.get("status")) {
                case 0: map.put("未激活", temp.get("num"));break;
                case 1: map.put("有效", temp.get("num"));break;
                case 2: map.put("已暂停", temp.get("num"));break;
                case 3: map.put("已停止", temp.get("num"));break;
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> getCustomerConsume(String month) {
        Session session = sessionFactory.getCurrentSession();
        Map<String, Object> map = new HashMap<>();

        String[] ts = month.split("-");
        int rYear = Integer.parseInt(ts[0]);
        int rMonth = Integer.parseInt(ts[1]);

        Query query = session.createSQLQuery("SELECT customer_id FROM consumption " +
                "WHERE year(time)=? AND month(time)=? GROUP BY customer_id HAVING sum(money)<100");
        query.setInteger(0, rYear);
        query.setInteger(1, rMonth);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        map.put("100元以下", list.size());

        query = session.createSQLQuery("SELECT customer_id FROM consumption " +
                "WHERE year(time)=? AND month(time)=? GROUP BY customer_id HAVING sum(money)>=100 AND sum(money)<500");
        query.setInteger(0, rYear);
        query.setInteger(1, rMonth);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        map.put("100~500元", list.size());

        query = session.createSQLQuery("SELECT customer_id FROM consumption " +
                "WHERE year(time)=? AND month(time)=? GROUP BY customer_id HAVING sum(money)>=500 AND sum(money)<1000");
        query.setInteger(0, rYear);
        query.setInteger(1, rMonth);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        map.put("500~1000元", list.size());

        query = session.createSQLQuery("SELECT customer_id FROM consumption " +
                "WHERE year(time)=? AND month(time)=? GROUP BY customer_id HAVING sum(money)>=1000");
        query.setInteger(0, rYear);
        query.setInteger(1, rMonth);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        map.put("1000元以上", list.size());

        return map;
    }

    @Override
    public Map<String, Object> getProducts(String month) {

        Session session = sessionFactory.getCurrentSession();
        Map<String, Object> map = new HashMap<>();

        String[] ts = month.split("-");
        int rYear = Integer.parseInt(ts[0]);
        int rMonth = Integer.parseInt(ts[1]);

        Query query = session.createSQLQuery("SELECT bi.product_id, p.name, sum(bi.number) AS num " +
                "FROM book_item bi LEFT JOIN book b ON " +
                "bi.book_id=b.id LEFT JOIN product p ON bi.product_id=p.id WHERE YEAR(b.buy_date)=? " +
                "AND month(b.buy_date)=? GROUP BY bi.product_id ORDER BY num DESC LIMIT 10");
        query.setInteger(0, rYear);
        query.setInteger(1, rMonth);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i< list.size(); i++) {
            Map temp = (Map)list.get(i);
            names.add(temp.get("name").toString());
            nums.add(Integer.parseInt(temp.get("num").toString()));
        }

        map.put("names", names);
        map.put("nums", nums);
        return map;
    }

    @Override
    public Map<String, Object> getSales(String month) {

        Session session = sessionFactory.getCurrentSession();
        Map<String, Object> map = new HashMap<>();

        String[] ts = month.split("-");
        int rYear = Integer.parseInt(ts[0]);
        int rMonth = Integer.parseInt(ts[1]);
        Query query = session.createSQLQuery("SELECT s.id, s.name, " +
                "IFNULL((SELECT sum(bi.number)" +
                "FROM book_item bi " +
                "LEFT JOIN book b ON bi.book_id=b.id " +
                "WHERE YEAR(b.buy_date)=? AND month(b.buy_date)=? " +
                "AND (b.status=1 OR b.status=3) " +
                "AND b.shop_id=s.id " +
                "GROUP BY b.shop_id ORDER BY b.shop_id ASC" +
                "), 0) AS num " +
                "FROM shop s");
        query.setInteger(0, rYear);
        query.setInteger(1, rMonth);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i< list.size(); i++) {
            Map temp = (Map)list.get(i);
            System.out.println(temp.get("shop_id"));
            names.add(temp.get("name").toString());
            nums.add(Integer.parseInt(temp.get("num").toString()));
        }
        map.put("names", names);
        map.put("saleNums", nums);

        query = session.createSQLQuery("SELECT s.id, s.name, " +
                "IFNULL((SELECT sum(bi.number)" +
                "FROM book_item bi " +
                "LEFT JOIN book b ON bi.book_id=b.id " +
                "WHERE YEAR(b.buy_date)=? AND month(b.buy_date)=? " +
                "AND (b.status<>2) " +
                "AND b.shop_id=s.id " +
                "GROUP BY b.shop_id ORDER BY b.shop_id ASC" +
                "), 0) AS num " +
                "FROM shop s");
        query.setInteger(0, rYear);
        query.setInteger(1, rMonth);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        names = new ArrayList<>();
        nums = new ArrayList<>();
        for (int i = 0; i< list.size(); i++) {
            Map temp = (Map)list.get(i);
            System.out.println(temp.get("shop_id"));
            names.add(temp.get("name").toString());
            nums.add(Integer.parseInt(temp.get("num").toString()));
        }
        map.put("bookNums", nums);

        return map;
    }
}
