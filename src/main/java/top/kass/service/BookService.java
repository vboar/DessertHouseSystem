package top.kass.service;

import top.kass.model.Book;
import top.kass.vo.ShoppingCart;

import java.util.List;
import java.util.Map;

public interface BookService {

    public Map<String, Object> createBook(int customerId, ShoppingCart shoppingCart);

    public List<Book> getBooksByCustomer(int customerId);

    public Map<String, Object> cancelBook(int id);

    public Book getBookById(int id);

    public void checkBookStatus(int customerId);

}
