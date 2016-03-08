package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.Shop;
import top.kass.model.User;
import top.kass.service.ShopService;
import top.kass.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ShopService shopService;

    // 店员管理页面
    @RequestMapping(value="/admin/user", method= RequestMethod.GET)
    public String userListPage() {
        return "admin/user/user_list";
    }

    // 新建店员页面
    @RequestMapping(value="/admin/user/add", method= RequestMethod.GET)
    public ModelAndView addUserPage() {
        List<Shop> shopList = shopService.getAllShops();
        ModelAndView modelAndView = new ModelAndView("admin/user/add_user", "shopList", shopList);
        return modelAndView;
    }

    // 新建店员操作
    @RequestMapping(value="/admin/user/add", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUser(String username, String name, String password, int role, int shop) {
        return userService.add(username, name, password, role, shop);
    }

    // 编辑店员页面
    @RequestMapping(value="/admin/user/edit", method= RequestMethod.GET)
    public ModelAndView editUserPage(int id) {
        User user = userService.getUserById(id);

        List<Shop> shopList = shopService.getAllShops();
        ModelAndView modelAndView = new ModelAndView("admin/user/edit_user");
        modelAndView.addObject("user", user);
        modelAndView.addObject("shopList", shopList);
        return modelAndView;
    }

    // 编辑店员操作
    @RequestMapping(value="/admin/user/edit", method= RequestMethod.POST)
    @ResponseBody
    public Map editUser(int id, String name, String password, int role, int shop) {
        return userService.edit(id, name, password, role, shop);
    }

    // 删除店员操作
    @RequestMapping(value="/admin/user/delete", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteUser(int id) {
        return null;
    }

}
