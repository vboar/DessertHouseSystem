package top.kass.service;

import top.kass.model.Customer;

import java.util.Map;

public interface CustomerService {

    public Map<String, Object> register(String phone, String password, String passwordAgain);

    public Map<String, Object> login(String phone, String password);

    public Map<String, Object> supplyInfo(int id, String name, String birthday, int gender,
                                          String province, String city, String bank);

    public Customer getCustomerById(int id);

    public Map<String, Object> validate(int id, String money);

}
