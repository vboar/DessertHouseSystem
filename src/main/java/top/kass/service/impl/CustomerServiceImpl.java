package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.CustomerDao;
import top.kass.model.Customer;
import top.kass.service.CustomerService;
import top.kass.util.Utils;

import java.sql.Date;
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
    public Map<String, Object> supplyInfo(int id, String name, String birthday, int gender,
                                          String province, String city, String bank) {
        Map<String, Object> map = new HashMap<>();
        name = name.trim();
        birthday = birthday.trim();
        province = province.trim();
        city = city.trim();
        bank = bank.trim();
        if (name.length() == 0 || birthday.length() == 0 || province.length() == 0 ||
                city.length() == 0 || bank.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
            return map;
        }

        Customer customer = customerDao.findById(id);
        customer.getCustomerInfo().setName(name);
        try {
            customer.getCustomerInfo().setBirthday(Date.valueOf(birthday));
        } catch (Exception e) {
            map.put("success", false);
            map.put("error", "生日格式不正确！");
            return map;
        }
        customer.getCustomerInfo().setGender((byte)gender);
        customer.getCustomerInfo().setCity(city);
        customer.getCustomerInfo().setProvince(province);
        customer.getCustomerAccount().setBankId(bank);
        customer = customerDao.update(customer);
        System.out.println(customer.getCustomerInfo().getName());
        map.put("success", true);
        map.put("customer_name", customer.getCustomerInfo().getName());
        return map;
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerDao.findById(id);
    }

}
