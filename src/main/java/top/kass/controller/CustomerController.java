package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.Book;
import top.kass.model.Customer;
import top.kass.model.Payment;
import top.kass.model.Point;
import top.kass.service.BookService;
import top.kass.service.CustomerService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BookService bookService;

    // 注册完完善个人信息
    @RequestMapping(value="/supplyInfo", method= RequestMethod.GET)
    public String supplyInfoPage() {
        return "customer/supply_info";
    }

    // 完善信息接口
    @RequestMapping(value="/supplyInfo", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postSupplyInfo(String name, String birthday, int gender,
                                 String province, String city, String bank,
                                 HttpSession session) {
        int id = (int)session.getAttribute("id");
        Map<String, Object> map = customerService.supplyInfo(id, name, birthday, gender, province, city, bank);
        if ((boolean)map.get("success")) {
            session.setAttribute("name", map.get("customer_name"));
        }
        return map;
    }

    // 充值并激活会员资格
    @RequestMapping(value="/validate", method= RequestMethod.GET)
    public ModelAndView validatePage(HttpSession session) {
        Customer customer = customerService.getCustomerById((int)session.getAttribute("id"));
        ModelAndView modelAndView = new ModelAndView("customer/validate", "customer", customer);
        return modelAndView;
    }

    // 激活接口
    @RequestMapping(value="/validate", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postValidate(String money, HttpSession session) {
        return customerService.validate((int)session.getAttribute("id"), money);
    }

    // 我的哆哆页面
    @RequestMapping(value="/dashboard", method= RequestMethod.GET)
    public ModelAndView dashboard(HttpSession session) {
        int id = (int)session.getAttribute("id");

        // 状态的检验、转换
        customerService.statusRecheck(id);

        Customer customer = customerService.getCustomerById(id);

        if ((int)customer.getStatus() == 3) {
            return new ModelAndView("common/error");
        }

        ModelAndView modelAndView = new ModelAndView("customer/dashboard");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    // 个人信息页面
    @RequestMapping(value="/user/info", method= RequestMethod.GET)
    public ModelAndView info(HttpSession session) {
        int id = (int)session.getAttribute("id");
        Customer customer = customerService.getCustomerById(id);
        ModelAndView modelAndView = new ModelAndView("customer/info");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    // 修改密码页面
    @RequestMapping(value="/user/password", method= RequestMethod.GET)
    public String password(HttpSession session) {
        return "customer/password";
    }

    // 修改密码操作
    @RequestMapping(value="/user/password", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postPassword(String old, String password, String passwordAgain,
                                             HttpSession session) {
        int id = (int)session.getAttribute("id");
        return customerService.password(id, old, password, passwordAgain);
    }

    // 我的积分页面
    @RequestMapping(value="/user/point", method= RequestMethod.GET)
    public ModelAndView point(HttpSession session) {
        int id = (int)session.getAttribute("id");
        Customer customer = customerService.getCustomerById(id);
        List<Point> pointList = customerService.getPointsByCustomer(id);
        ModelAndView modelAndView = new ModelAndView("customer/point");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("pointList", pointList);
        return modelAndView;
    }

    // 积分兑换操作
    @RequestMapping(value="/user/point/exchange", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pointExchange(int point, HttpSession session) {
        int id = (int)session.getAttribute("id");
        return customerService.exchangePoint(id, point);
    }

    // 我的缴费页面
    @RequestMapping(value="/user/payment", method= RequestMethod.GET)
    public ModelAndView payment(HttpSession session) {
        int id = (int)session.getAttribute("id");
        Customer customer = customerService.getCustomerById(id);
        List<Payment> paymentList= customerService.getPaymentsByCustomer(id);
        ModelAndView modelAndView = new ModelAndView("customer/payment");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("paymentList", paymentList);
        return modelAndView;
    }

    // 充值页面
    @RequestMapping(value="/user/recharge", method= RequestMethod.GET)
    public ModelAndView rechargePage(HttpSession session) {
        int id = (int)session.getAttribute("id");
        Customer customer = customerService.getCustomerById(id);
        ModelAndView modelAndView = new ModelAndView("customer/recharge");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    // 充值操作
    @RequestMapping(value="/user/recharge", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> recharge(int money, String password, HttpSession session) {
        int id = (int)session.getAttribute("id");
        return customerService.recharge(id, money, password);
    }

    // 停止账户操作
    @RequestMapping(value="/user/stop", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> stop(HttpSession session) {
        int id = (int)session.getAttribute("id");
        return customerService.stop(id);
    }

    // 我的订单页面
    @RequestMapping(value="/user/order", method= RequestMethod.GET)
    public ModelAndView orderPage(HttpSession session) {
        int id = (int)session.getAttribute("id");
        ModelAndView modelAndView = new ModelAndView("customer/order");
        List<Book> list = bookService.getBooksByCustomer(id);
        modelAndView.addObject("list", list);
        return modelAndView;
    }

}
