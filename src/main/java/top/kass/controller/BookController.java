package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.PlanItem;
import top.kass.model.Product;
import top.kass.model.Shop;
import top.kass.service.BookService;
import top.kass.service.PlanService;
import top.kass.service.ProductService;
import top.kass.vo.ShoppingCart;
import top.kass.vo.ShoppingCartItem;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    @Autowired
    private PlanService planService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BookService bookService;

    // 产品页面
    @RequestMapping(value="/product", method= RequestMethod.GET)
    public ModelAndView productPage(int id) {
        ModelAndView modelAndView = new ModelAndView("order/product");

        Product product = productService.getProductById(id);
        List<PlanItem> planItems = planService.getPlanItemsByProduct(id);

        modelAndView.addObject("product", product);
        modelAndView.addObject("planItems", planItems);

        return modelAndView;
    }

    // 放入购物车操作
    @RequestMapping(value="/shoppingCart/add", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addShoppingCart(int productId, int num, String date,
                                               HttpSession session) {
        Map<String, Object> map = new HashMap<>();

        if (session.getAttribute("shoppingCart") == null) {
            List<ShoppingCart> list = new ArrayList<>();
            session.setAttribute("shoppingCart", list);
        }

        PlanItem planItem = planService.getPlanItem(productId, date);
        int remaining = planItem.getRemaining();

        if (num > remaining) {
            map.put("success", false);
            map.put("error", "库存不足!");
            return map;
        }

        List<ShoppingCart> list = (List<ShoppingCart>)session.getAttribute("shoppingCart");
        boolean flag1 = false;
        for (ShoppingCart shoppingCart: list) {
            if (shoppingCart.getDate().equals(date)
                    && shoppingCart.getShopId() == planItem.getProduct().getShop().getId()) {
                flag1 = true;
                boolean flag = false;
                for (ShoppingCartItem item: shoppingCart.getItems()) {
                    if (item.getProductId() == productId) {

                        if (item.getNumber() + num > remaining) {
                            map.put("success", false);
                            map.put("error", "库存不足!");
                            return map;
                        }

                        item.setNumber(item.getNumber() + num);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    ShoppingCartItem temp = new ShoppingCartItem();
                    temp.setProductId(productId);
                    temp.setNumber(num);
                    temp.setProductName(planItem.getProduct().getName());
                    temp.setProductPrice(planItem.getPrice());
                    temp.setProductPoint(planItem.getPoint());
                    shoppingCart.getItems().add(temp);
                }
                break;
            }
        }
        if (!flag1) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setDate(date);
            shoppingCart.setShopId(planItem.getProduct().getShop().getId());
            shoppingCart.setShopName(planItem.getProduct().getShop().getName());
            List<ShoppingCartItem> items = new ArrayList<>();
            ShoppingCartItem item = new ShoppingCartItem();
            item.setProductId(productId);
            item.setProductName(planItem.getProduct().getName());
            item.setProductPrice(planItem.getPrice());
            item.setProductPoint(planItem.getPoint());
            item.setNumber(num);
            items.add(item);
            shoppingCart.setItems(items);
            list.add(shoppingCart);
        }

        System.out.println(list.size());

        map.put("success", true);
        return map;
    }

    // 购物车页面
    @RequestMapping(value="/shoppingCart", method= RequestMethod.GET)
    public ModelAndView shoppingCartPage(HttpSession session) {
        List<ShoppingCart> list = (List<ShoppingCart>)session.getAttribute("shoppingCart");
        ModelAndView modelAndView = new ModelAndView("order/shopping_cart");
        modelAndView.addObject("list", list);

        return modelAndView;
    }

    // 修改购物车item操作
    @RequestMapping(value="/shoppingCart/edit", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editShoppingCart(int productId, String date, int num, HttpSession session) {
        Map<String, Object> map = new HashMap<>();

        List<ShoppingCart> list = (List<ShoppingCart>)session.getAttribute("shoppingCart");
        PlanItem planItem = planService.getPlanItem(productId, date);

        for (ShoppingCart shoppingCart: list) {
            if (shoppingCart.getDate().equals(date) &&
                    shoppingCart.getShopId() == planItem.getProduct().getShop().getId()) {
                for (ShoppingCartItem item: shoppingCart.getItems()) {
                    if (item.getProductId() == productId) {

                        int remaining = planItem.getRemaining()-item.getNumber();
                        if (num > remaining) {
                            map.put("success", false);
                            map.put("remaing", remaining);
                            map.put("error", "库存不足!");
                            return map;
                        } else {
                            item.setNumber(num);
                        }
                        break;
                    }
                }
                break;
            }
        }
        map.put("success", true);
        return map;
    }

    // 删除购物车item操作
    @RequestMapping(value="/shoppingCart/delete", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteShoppingCart(int productId, String date, HttpSession session) {
        Map<String, Object> map = new HashMap<>();

        List<ShoppingCart> list = (List<ShoppingCart>)session.getAttribute("shoppingCart");

        PlanItem planItem = planService.getPlanItem(productId, date);

        for (ShoppingCart shoppingCart: list) {
            if (shoppingCart.getDate().equals(date) &&
                    shoppingCart.getShopId() == planItem.getProduct().getShop().getId()) {
                for (ShoppingCartItem item: shoppingCart.getItems()) {
                    if (item.getProductId() == productId) {
                        shoppingCart.getItems().remove(item);
                        if (shoppingCart.getItems().size() == 0) {
                            list.remove(shoppingCart);
                        }
                        break;
                    }
                }
                break;
            }
        }

        map.put("success", true);
        return map;
    }

    // 预订操作
    @RequestMapping(value="/product/book", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> book(int shopId, String date, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        int customerId = (int)session.getAttribute("id");
        List<ShoppingCart> list = (List<ShoppingCart>)session.getAttribute("shoppingCart");
        ShoppingCart shoppingCart = new ShoppingCart();
        for (ShoppingCart cart: list) {
            if (cart.getDate().equals(date) && cart.getShopId() == shopId) {
                shoppingCart = cart;
                break;
            }
        }
        map = bookService.createBook(customerId, shoppingCart);
        if ((boolean)map.get("success")) {
            list.remove(shoppingCart);
        }
        return map;
    }


}
