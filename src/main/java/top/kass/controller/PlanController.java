package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.Product;
import top.kass.model.Shop;
import top.kass.service.PlanService;
import top.kass.service.ShopService;
import top.kass.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class PlanController {

    @Autowired
    private PlanService planService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShopService shopService;

    // 产品计划管理页面
    @RequestMapping(value="/admin/plan", method= RequestMethod.GET)
    public String planListPage(HttpSession session) {
        int userId = (int)session.getAttribute("id");
        return "admin/plan/plan_list";
    }

    // 产品计划详情页面
    @RequestMapping(value="/admin/plan/detail", method= RequestMethod.GET)
    public String planDetailPage() {
        return "admin/plan/plan_detail";
    }

    // 制定产品计划页面
    @RequestMapping(value="/admin/plan/add", method= RequestMethod.GET)
    public ModelAndView addPlanPage(HttpSession session) {
        // 应该有一个最近一次计划最后的时间作为开始时间，默认结束时间为7天后
        int userId = (int)session.getAttribute("id");
        Shop shop = userService.getUserById(userId).getShop();
        ModelAndView modelAndView = new ModelAndView("admin/plan/add_plan");
        modelAndView.addObject("shop", shop);
        return modelAndView;
    }

    // 制定产品计划操作
    @RequestMapping(value="/admin/plan/add", method= RequestMethod.POST)
    public String addPlan() {
        return "";
    }

    // 编辑产品计划页面
    @RequestMapping(value="/admin/plan/edit", method= RequestMethod.GET)
    public String editPlanPage() {
        return "admin/plan/edit_plan";
    }

    // 编辑产品计划操作
    @RequestMapping(value="/admin/plan/edit", method= RequestMethod.POST)
    public String editPlan() {
        return "";
    }

    // 获得本店所有产品
    @RequestMapping(value="/admin/plan/products", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProducts(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        int userId = (int)session.getAttribute("id");
        Shop shop = userService.getUserById(userId).getShop();
        Set<Product> productSet = shopService.getShopById(shop.getId()).getProducts();
        map.put("products", productSet);
        return map;
    }

}
