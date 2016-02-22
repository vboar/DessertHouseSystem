package top.kass.service;

import java.util.Map;

public interface CustomerService {

    public Map<String, Object> register(String phone, String password, String passwordAgain);

}
