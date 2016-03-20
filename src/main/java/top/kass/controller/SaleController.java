package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.*;
import top.kass.service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SaleController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private ConsumptionService consumptionService;
    @Autowired
    private PlanService planService;

    // 已预订的销售页面
    @RequestMapping(value="/admin/sale/order", method= RequestMethod.GET)
    public ModelAndView orderSalePage(HttpSession session) {
        int id = (int)session.getAttribute("id");
        User user = userService.getUserById(id);
        Shop shop = user.getShop();
        ModelAndView modelAndView = new ModelAndView("admin/sale/order_sale");
        modelAndView.addObject("shop", shop);
        return modelAndView;
    }

    // 直接购买的销售页面
    @RequestMapping(value="/admin/sale/buy", method= RequestMethod.GET)
    public ModelAndView salePage(HttpSession session) {
        int id = (int)session.getAttribute("id");
        User user = userService.getUserById(id);
        Shop shop = user.getShop();
        List<PlanItem> planItemList = planService.getTodayPlanItemsByShop(shop.getId());
        ModelAndView modelAndView = new ModelAndView("admin/sale/sale");
        modelAndView.addObject("shop", shop);
        modelAndView.addObject("planItemList", planItemList);
        return modelAndView;
    }

    // 获得客户的订单
    @RequestMapping(value="/admin/sale/getBooks", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getBooks(int code) {
        Map<String, Object> map = new HashMap<>();
        Customer customer = customerService.getCustomerByCode(code);
        List<Book> bookList;
        if (customer == null) {
            bookList = new ArrayList<>();
        }
        bookList = bookService.getTodayBooksByCustomer(customer.getId());
        map.put("bookList", bookList);
        return map;
    }

    // 预订的支付/销售操作
    @RequestMapping(value="/admin/sale/payForBook", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> payForBook(int id, int type) {
        Map<String, Object> map = new HashMap<>();
        return saleService.payForBook(id, type);
    }

    // 现场销售操作
    @RequestMapping(value="/admin/sale/pay", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPlan(@RequestBody String data, HttpSession session) {
        int id = (int)session.getAttribute("id");
        User user = userService.getUserById(id);
        int shopId = user.getShop().getId();
        Map<String, Object> map = saleService.pay(data, shopId);
        return map;
    }

    // 我的消费页面
    @RequestMapping(value="/user/consume", method= RequestMethod.GET)
    public ModelAndView payment(HttpSession session) {
        int id = (int)session.getAttribute("id");
        List<Consumption> consumptionList = consumptionService.getConsumptionsByCustomer(id);
        ModelAndView modelAndView = new ModelAndView("customer/consume");
        modelAndView.addObject("consumptionList", consumptionList);
        return modelAndView;
    }

}
