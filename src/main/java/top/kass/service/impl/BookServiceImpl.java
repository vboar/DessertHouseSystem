package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.BookDao;
import top.kass.dao.PlanDao;
import top.kass.model.Book;
import top.kass.model.PlanItem;
import top.kass.service.BookService;
import top.kass.service.PlanService;
import top.kass.vo.ShoppingCart;
import top.kass.vo.ShoppingCartItem;

import java.sql.Date;
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
        for (Book book: list) {
            java.util.Date uDate = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sDate = sdf.format(uDate);
            Date date = Date.valueOf(sDate);
            if (date.compareTo(book.getBuyDate()) > 0 && book.getStatus() == 0) {
                book.setStatus((byte)3);
                bookDao.update(book);
                // TODO 过期订单 强行消费
            }
        }
    }

    @Override
    public List<Book> getTodayBooksByCustomer(int customerId) {
        return bookDao.getTodayByCustomerId(customerId);
    }

}
