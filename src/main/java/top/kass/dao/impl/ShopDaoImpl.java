package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.kass.dao.ShopDao;
import top.kass.model.Shop;

import java.util.List;

@Repository
public class ShopDaoImpl implements ShopDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Shop create(String name, String description, String address) {

        Session session = sessionFactory.getCurrentSession();

        Shop shop = new Shop();
        shop.setName(name);
        shop.setDescription(description);
        shop.setAddress(address);
        shop.setStatus((byte)1);
        session.save(shop);
        return shop;
    }

    @Override
    public Shop update(Shop shop) {
        Session session = sessionFactory.getCurrentSession();
        session.save(shop);
        return shop;
    }

    @Override
    public Shop findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Shop where id=:id");
        query.setInteger("id", id);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Shop)query.list().get(0);
        }
    }

    @Override
    public List<Shop> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Shop");
        List<Shop> shopList = (List<Shop>)query.list();
        return shopList;
    }

    @Override
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete Shop where id=:id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

}
