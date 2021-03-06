package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.Plan;
import top.kass.model.Product;
import top.kass.model.Shop;
import top.kass.service.PlanService;
import top.kass.service.ShopService;
import top.kass.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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
    public ModelAndView planListPage(HttpSession session) {
        int userId = (int)session.getAttribute("id");
        int role = (int)session.getAttribute("role");
        Shop shop = userService.getUserById(userId).getShop();
        ModelAndView modelAndView;
        if (role == 4) {
            modelAndView = new ModelAndView("admin/plan/plan_list_manager");
        } else {
            modelAndView = new ModelAndView("admin/plan/plan_list");
        }
        modelAndView.addObject("shop", shop);
        return modelAndView;
    }

    // 产品计划详情页面
    @RequestMapping(value="/admin/plan/detail", method= RequestMethod.GET)
    public ModelAndView planDetailPage(int id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("admin/plan/plan_detail");
        Plan plan = planService.getPlanById(id);
        Shop shop = shopService.getShopById(plan.getShopId());
        int role = (int)session.getAttribute("role");
        modelAndView.addObject("plan", plan);
        modelAndView.addObject("shop", shop);
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    // 制定产品计划页面
    @RequestMapping(value="/admin/plan/add", method= RequestMethod.GET)
    public ModelAndView addPlanPage(HttpSession session) {
        int userId = (int)session.getAttribute("id");
        Shop shop = userService.getUserById(userId).getShop();
        String[] dates = planService.getDate(shop.getId());
        ModelAndView modelAndView = new ModelAndView("admin/plan/add_plan");
        modelAndView.addObject("shop", shop);
        modelAndView.addObject("dates", dates);
        return modelAndView;
    }

    // 制定产品计划操作
    @RequestMapping(value="/admin/plan/add", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPlan(@RequestBody String data, HttpSession session) {
        int userId = (int)session.getAttribute("id");
        Shop shop = userService.getUserById(userId).getShop();
        planService.addPlan(data, shop.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    // 编辑产品计划页面
    @RequestMapping(value="/admin/plan/edit", method= RequestMethod.GET)
    public ModelAndView editPlanPage(int id) {
        ModelAndView modelAndView = new ModelAndView("admin/plan/edit_plan");
        Plan plan = planService.getPlanById(id);
        Shop shop = shopService.getShopById(plan.getShopId());
        modelAndView.addObject("plan", plan);
        modelAndView.addObject("shop", shop);
        return modelAndView;
    }

    // 编辑产品计划操作
    @RequestMapping(value="/admin/plan/edit", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>  editPlan(@RequestBody String data) {
        planService.edit(data);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    // 获得本店所有产品JSON
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

    // 根据店面ID获取计划
    @RequestMapping(value="/admin/plan/listByShop", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPlanListByShop(int id) {
        Map<String, Object> map = new HashMap<>();
        List<Plan> planList = planService.getPlansByShop(id);
        map.put("planList", planList);
        return map;
    }

    // 根据状态获取计划
    @RequestMapping(value="/admin/plan/listByStatus", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPlanListByStatus(int status) {
        Map<String, Object> map = new HashMap<>();
        List<Plan> planList = planService.getPlansByStatus(status);
        map.put("planList", planList);
        return map;
    }

    // 获得某次计划JSON
    @RequestMapping(value="/admin/plan/detail", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPlanById(int id) {
        Map<String, Object> map = new HashMap<>();
        Plan plan = planService.getPlanById(id);
        // Shop shop = shopService.getShopById(plan.getShopId());
        map.put("plan", plan);
        // map.put("shop", shop);
        return map;
    }

    // 更新状态
    @RequestMapping(value="/admin/plan/status", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateStatus(int id, int status) {
        Map<String, Object> map = new HashMap<>();
        planService.updateStatus(id, status);
        map.put("success", true);
        return map;
    }

}
