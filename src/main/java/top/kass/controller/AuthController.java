package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kass.model.User;
import top.kass.model.UserShop;
import top.kass.service.CustomerService;
import top.kass.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String register() {
        return "auth/register";
    }

    @RequestMapping(value="/register", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postRegister(@RequestParam String phone, @RequestParam String password,
                                            @RequestParam String passwordAgain, HttpSession session) {
        Map<String, Object> map = customerService.register(phone, password, passwordAgain);
        if ((boolean)map.get("success")) {
            session.setAttribute("id", map.get("customer_id"));
            session.setAttribute("name", map.get("customer_name"));
            session.setAttribute("role", 5);
        }
        return map;
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login() {
        return "auth/login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postLogin(String phone, String password, HttpSession session) {
        Map<String, Object> map = customerService.login(phone, password);
        if ((boolean)map.get("success")) {
            session.setAttribute("id", map.get("customer_id"));
            session.setAttribute("name", map.get("customer_name"));
            session.setAttribute("role", 5);
        }
        return map;
    }

    @RequestMapping(value="/admin/login", method= RequestMethod.GET)
    public String adminLogin() {
        return "admin/auth/login";
    }

    @RequestMapping(value="/admin/login", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postAdminLogin(String username, String password, HttpSession session) {
        Map<String, Object> map = userService.login(username, password);
        if ((boolean)map.get("success")) {
            session.setAttribute("id", map.get("user_id"));
            session.setAttribute("role", map.get("role"));
            session.setAttribute("name", map.get("user_name"));
        }
        return map;
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("id");
        session.removeAttribute("role");
        return "redirect:/";
    }

    // 修改密码页面
    @RequestMapping(value="/admin/password", method= RequestMethod.GET)
    public String adminPasswordPage() {
        return "admin/auth/password";
    }

    // 修改密码操作
    @RequestMapping(value="/admin/password", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> adminPassword(String old, String password, String passwordAgain,
                                        HttpSession session) {
        int id = (int)session.getAttribute("id");
        return userService.password(id, old, password, passwordAgain);
    }


}
