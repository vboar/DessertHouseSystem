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

}
