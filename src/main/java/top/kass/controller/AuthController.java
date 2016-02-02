package top.kass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {

    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String register() {
        return "auth/register";
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
