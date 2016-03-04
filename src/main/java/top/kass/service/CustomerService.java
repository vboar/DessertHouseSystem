package top.kass.service;

import java.util.Map;

public interface CustomerService {

    public Map<String, Object> register(String phone, String password, String passwordAgain);

    public Map<String, Object> login(String phone, String password);

    public Map<String, Object> supplyInfo(int id, String name, String birthday, int gender,
                                          String province, String city, int bank);

}
