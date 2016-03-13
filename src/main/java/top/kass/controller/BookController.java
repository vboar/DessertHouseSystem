package top.kass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BookController {

    // 放入购物车操作
    @RequestMapping(value="/shoppingCart/add", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addShoppingCart(int productId, int num, String date,
                                               HttpSession session) {
        Map<String, Object> map = new HashMap<>();
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
    public Map<String, Object> editShoppingCart(int itemId, int num, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    // 删除购物车item操作
    @RequestMapping(value="/shoppingCart/delete", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteShoppingCart(int itemId, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
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
