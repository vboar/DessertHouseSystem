package top.kass.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.UserDao;
import top.kass.model.*;

import java.util.Iterator;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User test() {
        Session session = sessionFactory.getCurrentSession();
        User user = (User)session.get(User.class, 3);
        return user;
    }

    @Override
    public void test1() {
        Session session = sessionFactory.getCurrentSession();

    }

    @Override
    public UserShop test2() {
        Session session = sessionFactory.getCurrentSession();
        UserShop userShop = (UserShop)session.get(UserShop.class, 3);
        return userShop;
    }

    @Override
    public Shop test3() {
        Session session = sessionFactory.getCurrentSession();
        Shop shop = (Shop)session.get(Shop.class, 1);
        Set<UserShop> userShops = shop.getUserShops();
        Iterator<UserShop> it = userShops.iterator();
        return shop;
    }

    @Override
    public Customer test4() {
        Session session = sessionFactory.getCurrentSession();
        Customer customer = (Customer)session.get(Customer.class, 1);
        return customer;
    }


}
