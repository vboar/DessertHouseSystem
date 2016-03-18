package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kass.model.Shop;
import top.kass.service.PlanService;
import top.kass.vo.IndexProduct;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private PlanService planService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index() {
        return "index";
    }

    // 获得产品、查找茶品
    @RequestMapping(value="/getProducts", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> addPlan(int shopId, String key) {
        Map<String, Object> map = new HashMap<>();

        List<IndexProduct> list = planService.getProductsByShopAndSearch(shopId, key);
        map.put("list", list);

        return map;
    }

    @RequestMapping(value="/admin", method= RequestMethod.GET)
    public String adminIndex(HttpSession session) {
        int role = (int)session.getAttribute("role");
        String url;
        switch (role) {
            case 1: // 系统管理员
                url = "/admin/shop";
                break;
            case 2: // 分店服务员
                url = "/admin/sale";
                break;
            case 3: // 总店服务员
                url = "/admin/plan";
                break;
            default:// 总经理
                url = "/admin/plan";
        }
        return "redirect:" + url;
    }
}
