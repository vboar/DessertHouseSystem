package top.kass.service.impl;

import org.springframework.stereotype.Service;
import top.kass.service.CustomerService;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Map<String, Object> register(String phone, String password, String passwordAgain) {
        Map<String, Object> map = new HashMap<>();
        if (!password.equals(passwordAgain)) {
            map.put("success", false);
            map.put("error", "两次输入的密码不对应！");
            return map;
        }
        map.put("success", true);
        return map;
    }

}
