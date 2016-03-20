package top.kass.service;

import java.util.Map;

public interface StatisticService {

    public Map<String, Object> getCustomerAge();

    public Map<String, Object> getCustomerGender();

    public Map<String, Object> getCustomerCity();

    public Map<String, Object> getCustomerStatus();

    public Map<String, Object> getCustomerConsume(String month);

}
