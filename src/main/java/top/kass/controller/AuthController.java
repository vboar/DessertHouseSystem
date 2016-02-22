package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kass.model.User;
import top.kass.service.CustomerService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String register() {
        return "auth/register";
    }

    @RequestMapping(value="/register", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postRegister(@RequestParam String phone, @RequestParam String password,
                             @RequestParam String passwordAgain) {
        return customerService.register(phone, password, passwordAgain);
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login() {
        return "auth/login";
    }

    @RequestMapping(value="/admin/login", method= RequestMethod.GET)
    public String adminLogin() {
        return "admin/auth/login";
    }



}
