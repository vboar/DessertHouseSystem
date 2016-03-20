package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kass.service.StatisticService;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    // 统计 - 会员信息
    @RequestMapping(value="/admin/statistics/customer", method= RequestMethod.GET)
    public ModelAndView customerPage() {

        ModelAndView modelAndView = new ModelAndView("admin/statistic/customer");

        return modelAndView;
    }

    @RequestMapping(value="/admin/statistics/customer/age", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> customerAge() {
        return statisticService.getCustomerAge();
    }

    @RequestMapping(value="/admin/statistics/customer/gender", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> customerGender() {
        return statisticService.getCustomerGender();
    }

    @RequestMapping(value="/admin/statistics/customer/city", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> customerCity() {
        return statisticService.getCustomerCity();
    }

    @RequestMapping(value="/admin/statistics/customer/status", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> customerStatus() {
        return statisticService.getCustomerStatus();
    }

    @RequestMapping(value="/admin/statistics/customer/consume", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> customerConsume(String month) {
        return statisticService.getCustomerConsume(month);
    }

    // 统计 - 预订销售
    @RequestMapping(value="/admin/statistics/sale", method= RequestMethod.GET)
    public ModelAndView salePage() {

        ModelAndView modelAndView = new ModelAndView("admin/statistic/sale");

        return modelAndView;
    }

    @RequestMapping(value="/admin/statistics/sale/getSales", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> saleGetSales(String month) {
        return statisticService.getSales(month);
    }

    // 统计 - 热卖产品
    @RequestMapping(value="/admin/statistics/product", method= RequestMethod.GET)
    public ModelAndView productPage() {

        ModelAndView modelAndView = new ModelAndView("admin/statistic/product");

        return modelAndView;
    }

    @RequestMapping(value="/admin/statistics/product/getProducts", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> productGetProducts(String month) {
        return statisticService.getProducts(month);
    }

}
