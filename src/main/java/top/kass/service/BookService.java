package top.kass.service;

import top.kass.model.Book;
import top.kass.vo.ShoppingCart;

import java.util.List;
import java.util.Map;

public interface BookService {

    public Map<String, Object> createBook(int customerId, ShoppingCart shoppingCart);

    public List<Book> getBooksByCustomer(int customerId);

}
