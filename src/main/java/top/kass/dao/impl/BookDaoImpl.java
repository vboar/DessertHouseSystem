package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kass.dao.BookDao;
import top.kass.dao.CustomerDao;
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
    public List<Book> getByCustomerId(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Book where customerId=:customerId order by createTime desc");
        query.setInteger("customerId", customerId);
        return query.list();
    }


}
