package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kass.service.CustomerService;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 注册完完善个人信息
    @RequestMapping(value="/supplyInfo", method= RequestMethod.GET)
    public String supplyInfoPage() {
        return "customer/supply_info";
    }

    // 完善信息接口
    @RequestMapping(value="/supplyInfo", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postSupplyInfo(String name, String birthday, int gender,
                                 String province, String city, int bank,
                                 HttpSession session) {
        int id = (int)session.getAttribute("id");
        Map<String, Object> map = customerService.supplyInfo(id, name, birthday, gender, province, city, bank);
        return map;
    }

}
