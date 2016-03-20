package top.kass.dao;

import org.json.JSONArray;
import top.kass.model.Book;
import top.kass.vo.ShoppingCart;

import java.util.List;

public interface BookDao {

    public Book create(int customerId, ShoppingCart shoppingCart);

    public List<Book> getByCustomerId(int customerId);

    public Book update(Book book);

    public Book findById(int id);

    public List<Book> getTodayByCustomerId(int customerId);

    public Book createBySale(int customerId, int shopId, JSONArray items);

}
