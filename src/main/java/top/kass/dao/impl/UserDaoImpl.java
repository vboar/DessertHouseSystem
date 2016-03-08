package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.kass.dao.UserDao;
import top.kass.model.Shop;
import top.kass.model.User;
import top.kass.util.Utils;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where username=:username");
        query.setString("username", username);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (User)query.list().get(0);
        }
    }

    @Override
    public User findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where id=:id");
        query.setInteger("id", id);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (User)query.list().get(0);
        }
    }

    @Override
    public User update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

    @Override
    @Transactional
    public User create(String username, String name, String password, int role, int shop) {
        Session session = sessionFactory.getCurrentSession();

        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setPassword(Utils.md5(password));
        user.setRole((byte)role);
        session.save(user);
        return user;
    }

    @Override
    public User update(int id, String name, String password, int role, int shop) {
        Session session = sessionFactory.getCurrentSession();

        User user = findById(id);
        user.setName(name);
        user.setPassword(Utils.md5(password));
        user.setRole((byte)role);
        session.save(user);



        return user;
    }
}
