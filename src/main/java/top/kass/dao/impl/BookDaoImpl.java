package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.BookDao;
import top.kass.dao.CustomerDao;
import top.kass.dao.PlanDao;
import top.kass.model.Book;
import top.kass.model.BookItem;
import top.kass.model.Customer;
import top.kass.model.Product;
import top.kass.vo.ShoppingCart;
import top.kass.vo.ShoppingCartItem;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PlanDao planDao;

    @Override
    public Book create(int customerId, ShoppingCart shoppingCart) {
        Session session = sessionFactory.getCurrentSession();

        Customer customer = customerDao.findById(customerId);

        Book book = new Book();
        book.setCustomerId(customerId);
        book.setShopId(shoppingCart.getShopId());
        book.setCreateTime(new Timestamp(System.currentTimeMillis()));
        book.setDiscount(customer.getCustomerAccount().getVipLevel().getDiscount());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            book.setBuyDate(new Date(sdf.parse(shoppingCart.getDate()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        session.save(book);
        session.flush();

        double originalTotal = 0;
        int totalPoint = 0;
        Set<BookItem> items = new HashSet<>();
        for (ShoppingCartItem item: shoppingCart.getItems()) {
            BookItem bookItem = new BookItem();
            bookItem.setBook(book);
            bookItem.setNumber(item.getNumber());
            bookItem.setPrice(item.getProductPrice());
            Product product = new Product();
            product.setId(item.getProductId());
            bookItem.setProduct(product);
            originalTotal += item.getProductPrice()*item.getNumber();
            totalPoint += item.getProductPoint()*item.getNumber();

            items.add(bookItem);

            // 预订商品减库存
            planDao.updatePlanItem(product.getId(), shoppingCart.getDate(), item.getNumber());

        }

        book.setBookItems(items);
        book.setOriginalTotal(originalTotal);
        book.setTotalPoint(totalPoint);
        book.setActualTotal(originalTotal*book.getDiscount());
        book.setStatus((byte)0);

        session.save(book);
        session.flush();

        return book;
    }

    @Override
    public Book createBySale(int customerId, int shopId, JSONArray items) {
        Session session = sessionFactory.getCurrentSession();

        Customer customer = customerDao.findById(customerId);

        Book book = new Book();
        book.setCustomerId(customerId);
        book.setShopId(shopId);
        book.setCreateTime(new Timestamp(System.currentTimeMillis()));
        book.setDiscount(customer.getCustomerAccount().getVipLevel().getDiscount());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        book.setBuyDate(new Date(System.currentTimeMillis()));
        session.save(book);
        session.flush();

        double originalTotal = 0;
        int totalPoint = 0;
        Set<BookItem> bookItems = new HashSet<>();
        for (int i = 0; i < items.length(); i++) {
            BookItem bookItem = new BookItem();
            JSONObject item = (JSONObject)items.get(i);
            bookItem.setBook(book);
            bookItem.setNumber((int)item.get("number"));
            bookItem.setPrice((double)item.get("price"));
            Product product = new Product();
            product.setId(Integer.parseInt(item.get("productId").toString()));
            bookItem.setProduct(product);
            originalTotal += ((double)item.get("price"))*((int)item.get("number"));
            totalPoint += ((int)item.get("point"))*((int)item.get("number"));
            bookItems.add(bookItem);

            // 预订商品减库存
            planDao.updatePlanItem(product.getId(), sdf.format(new java.util.Date()), (int)item.get("number"));
        }

        book.setBookItems(bookItems);
        book.setOriginalTotal(originalTotal);
        book.setTotalPoint(totalPoint);
        book.setActualTotal(originalTotal*book.getDiscount());
        book.setStatus((byte)1);

        session.save(book);
        session.flush();

        return book;
    }

    @Override
    public List<Book> getByCustomerId(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Book where customerId=:customerId order by createTime desc");
        query.setInteger("customerId", customerId);
        return query.list();
    }

    @Override
    public Book update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
        session.flush();
        return book;
    }

    @Override
    public Book findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Book where id=:id");
        query.setInteger("id", id);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Book) query.list().get(0);
        }
    }

    @Override
    public List<Book> getTodayByCustomerId(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateS = sdf.format(date);
        System.out.println("aaa " + dateS);
        Query query = session.createQuery("from Book where customerId=:customerId and buyDate=:date " +
                "and status=0 order by createTime desc");
        query.setInteger("customerId", customerId);
        query.setString("date", dateS);
        return query.list();
    }


}
