package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.PlanItem;
import top.kass.model.Shop;
import top.kass.service.PlanService;
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
            item.setProductId(planItem.getProduct().getId());
            item.setNumber(num);
            items.add(item);
            shoppingCart.setItems(items);
        }

        map.put("success", true);
        return map;
    }

    // 购物车页面
    @RequestMapping(value="/shoppingCart", method= RequestMethod.GET)
    public ModelAndView shoppingCartPage() {
        ModelAndView modelAndView = new ModelAndView();
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
        return map;
    }



}
