package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.*;
import top.kass.model.Book;
import top.kass.model.Consumption;
import top.kass.model.Customer;
import top.kass.model.PlanItem;
import top.kass.service.BookService;
import top.kass.service.PlanService;
import top.kass.vo.ShoppingCart;
import top.kass.vo.ShoppingCartItem;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private PlanService planService;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private ConsumptionDao consumptionDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PointDao pointDao;

    @Override
    public Map<String, Object> createBook(int customerId, ShoppingCart shoppingCart) {

        Map<String, Object> map = new HashMap<>();

        for (ShoppingCartItem cartItem: shoppingCart.getItems()) {
            PlanItem planItem = planService.getPlanItem(cartItem.getProductId(), shoppingCart.getDate());
            if (cartItem.getNumber() > planItem.getRemaining()) {
                map.put("success", false);
                map.put("error", "产品 " + cartItem.getProductName() + " 库存不足！");
                return map;
            }
        }

        Book book = bookDao.create(customerId, shoppingCart);
        map.put("success", true);
        map.put("id", book.getId());
        return map;
    }

    @Override
    public List<Book> getBooksByCustomer(int customerId) {
        return bookDao.getByCustomerId(customerId);
    }

    @Override
    public Map<String, Object> cancelBook(int id) {
        Book book = bookDao.findById(id);
        book.setStatus((byte)2);
        bookDao.update(book);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.findById(id);
    }

    @Override
    public void checkBookStatus(int customerId) {
        List<Book> list = bookDao.getByCustomerId(customerId);
        Customer customer = customerDao.findById(customerId);
        for (Book book: list) {
            java.util.Date uDate = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sDate = sdf.format(uDate);
            Date date = Date.valueOf(sDate);
            if (date.compareTo(book.getBuyDate()) > 0 && book.getStatus() == 0) {
                book.setStatus((byte)3);
                bookDao.update(book);
                // 过期订单 强行消费

                Consumption consumption = new Consumption();
                consumption.setPayType((byte)1);
                consumption.setBook(book);
                consumption.setCustomerId(customerId);
                consumption.setMoney(book.getActualTotal());
                consumption.setPoint(book.getTotalPoint());
                consumption.setTime(new Timestamp(System.currentTimeMillis()));
                consumptionDao.createOrUpdate(consumption);

                customer.getCustomerAccount().setBalance(
                        customer.getCustomerAccount().getBalance()-book.getActualTotal());
                customer.getCustomerAccount().setPoint(
                        customer.getCustomerAccount().getPoint()+book.getTotalPoint());
                customerDao.update(customer);

                book.setStatus((byte)1);
                book.setSaleTime(new Timestamp(System.currentTimeMillis()));
                bookDao.update(book);

                // 积分记录
                pointDao.create(customer.getId(), book.getTotalPoint(), 0, consumption.getId());
            }
        }
    }

    @Override
    public List<Book> getTodayBooksByCustomer(int customerId) {
        return bookDao.getTodayByCustomerId(customerId);
    }

}
