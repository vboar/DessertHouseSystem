package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.CustomerDao;
import top.kass.model.Customer;
import top.kass.service.CustomerService;
import top.kass.util.Utils;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Map<String, Object> register(String phone, String password, String passwordAgain) {
        Map<String, Object> map = new HashMap<>();
        phone = phone.trim();
        password = password.trim();
        passwordAgain = passwordAgain.trim();
        if (phone.length() == 0 || password.length() == 0 || passwordAgain.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
        } else if (!Utils.isMobileNumber(phone)) {
            map.put("success", false);
            map.put("error", "请输入正确的手机号码！");
        } else if (!password.equals(passwordAgain)) {
            map.put("success", false);
            map.put("error", "两次输入的密码不对应！");
        } else if (customerDao.phoneExist(phone)) {
            map.put("success", false);
            map.put("error", "该手机号码已经注册了会员！");
        } else {
            Customer customer = customerDao.create(phone, password);
            map.put("success", true);
            map.put("customer_id", customer.getId());
            map.put("customer_name", customer.getCustomerInfo().getName());
        }
        return map;
    }

    @Override
    public Map<String, Object> login(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        phone = phone.trim();
        password = password.trim();
        if (phone.length() == 0 || password.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
        } else if (!Utils.isMobileNumber(phone)) {
            map.put("success", false);
            map.put("error", "请输入正确的手机号码！");
        } else {
            Customer customer = customerDao.findByPhone(phone);
            if (customer == null) {
                map.put("success", false);
                map.put("error", "手机号或密码错误！");
            } else {
                if (!Utils.md5(password).equals(customer.getPassword())) {
                    map.put("success", false);
                    map.put("error", "手机号或密码错误！");
                } else {
                    map.put("success", true);
                    map.put("customer_id", customer.getId());
                    map.put("customer_name", customer.getCustomerInfo().getName());
                }
            }
        }
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> supplyInfo(int id, String name, String birthday, int gender,
                                          String province, String city, int bank) {
        Map<String, Object> map = new HashMap<>();

        return null;
    }

}
