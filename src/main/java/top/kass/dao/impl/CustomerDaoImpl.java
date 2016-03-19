package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.kass.dao.CustomerDao;
import top.kass.model.*;
import top.kass.util.Utils;

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

        Session session = sessionFactory.getCurrentSession();

        // Generate Code
        boolean flag = false;
        int code;
        do {
            code = Utils.generateCode();
            SQLQuery query = session.createSQLQuery("SELECT code FROM customer WHERE code = ?");
            query.setInteger(0, code);
            if (query.uniqueResult() == null) flag = true;
        } while (!flag);

        Customer customer = new Customer();
        customer.setCode(code);
        customer.setPhone(phone);
        customer.setPassword(Utils.md5(password));
        customer.setStatus((byte)0);
        session.save(customer);

        session.flush();

        CustomerAccount account = new CustomerAccount();
        account.setCustomerId(customer.getId());
        account.setPoint(0);
        account.setBalance(0);
        VipLevel vipLevel = (VipLevel) session.get(VipLevel.class, (byte)0);
        account.setVipLevel(vipLevel);
        customer.setCustomerAccount(account);

        CustomerInfo info = new CustomerInfo();
        info.setCustomerId(customer.getId());
        customer.setCustomerInfo(info);

        CustomerStatus status = new CustomerStatus();
        status.setCustomerId(customer.getId());
        customer.setCustomerStatus(status);

        session.flush();

        return customer;
    }

    @Override
    public Customer findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Customer where id=:id");
        query.setInteger("id", id);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Customer)query.list().get(0);
        }
    }

    @Override
    public Customer findByPhone(String phone) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Customer as customer where phone=:phone");
        query.setString("phone", phone);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Customer)query.list().get(0);
        }
    }

    @Override
    public Customer update(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(customer);
        session.flush();
        return customer;
    }

    @Override
    public Customer findByCode(int code) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Customer as customer where code=:code");
        query.setInteger("code", code);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Customer)query.list().get(0);
        }
    }


}
