package top.kass.dao.impl;


import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.CustomerDao;
import top.kass.model.Customer;
import top.kass.model.CustomerAccount;
import top.kass.model.CustomerInfo;
import top.kass.model.CustomerStatus;
import top.kass.util.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean phoneExist(String phone) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT phone FROM customer WHERE phone = ?");
        query.setString(0, phone);
        if (query.uniqueResult() == null) return false;
        return true;
    }

    @Override
    public Customer create(String phone, String password) {
        Customer customer = new Customer();
        customer.setCode(123);
        customer.setPhone(phone);
        customer.setPassword(Utils.md5(password));
        customer.setStatus((byte)0);

        Session session = sessionFactory.getCurrentSession();
        session.save(customer);

        System.out.println(customer.getId());

        return customer;
    }


}
