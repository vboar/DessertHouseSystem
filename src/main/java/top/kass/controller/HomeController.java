package top.kass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index() {
        return "index";
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
