package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.Shop;
import top.kass.service.ShopService;
import java.util.Map;

@Controller
public class ShopController {

    @Autowired
    private ShopService shopService;

    // 店员管理页面
    @RequestMapping(value="/admin/shop", method= RequestMethod.GET)
    public String shopListPage() {
        return "admin/shop/shop_list";
    }

    // 店面详情页面
    @RequestMapping(value="/admin/shop/detail", method= RequestMethod.GET)
    public String shopDetailPage() {
        return "admin/shop/shop_detail";
    }

    // 新建店面页面
    @RequestMapping(value="/admin/shop/add", method= RequestMethod.GET)
    public String addShopPage() {
        return "admin/shop/add_shop";
    }

    // 新建店面操作
    @RequestMapping(value="/admin/shop/add", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addShop(String name, String description, String address) {
        Map<String, Object> map = shopService.add(name, description, address);
        return map;
    }

    // 编辑店面页面
    @RequestMapping(value="/admin/shop/edit", method= RequestMethod.GET)
    public ModelAndView editShopPage(int id) {
        Shop shop = shopService.getShopById(id);
        ModelAndView modelAndView = new ModelAndView("admin/shop/edit_shop", "shop", shop);
        return modelAndView;
    }

    // 编辑店面操作
    @RequestMapping(value="/admin/shop/edit", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editShop(int id, String name, String description, String address) {
        Map<String, Object> map = shopService.edit(id, name, description, address);
        return map;
    }

    // 删除店面操作
    @RequestMapping(value="/admin/shop/delete", method= RequestMethod.POST)
    public String deleteShop() {
        return "";
    }

}
