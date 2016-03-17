package top.kass.service;

import top.kass.model.Customer;
import top.kass.model.Point;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    public Map<String, Object> register(String phone, String password, String passwordAgain);

    public Map<String, Object> login(String phone, String password);

    public Map<String, Object> supplyInfo(int id, String name, String birthday, int gender,
                                          String province, String city, String bank);

    public Customer getCustomerById(int id);

    public Map<String, Object> validate(int id, String money);

    public Map<String, Object> editInfo(int id, String name, String birthday, int gender,
                                        String province, String city);

    public Map<String, Object> password(int id, String old, String password, String passwordAgain);

    public Map<String, Object> exchangePoint(int id, int point);

    public List<Point> getPointsByCustomer(int id);

}
