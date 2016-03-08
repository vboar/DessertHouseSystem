package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.UserDao;
import top.kass.model.User;

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
}
