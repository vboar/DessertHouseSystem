package top.kass.dao;

import top.kass.model.Book;
import top.kass.vo.ShoppingCart;

import java.util.List;

public interface BookDao {

    public Book create(int customerId, ShoppingCart shoppingCart);

    public List<Book> getByCustomerId(int customerId);

    public Book update(Book book);

    public Book findById(int id);

}
