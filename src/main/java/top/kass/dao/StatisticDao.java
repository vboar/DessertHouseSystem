package top.kass.dao;

import java.util.Map;

public interface StatisticDao {

    public Map<String, Object> getCustomerAge();

    public Map<String, Object> getCustomerGender();

    public Map<String, Object> getCustomerCity();

    public Map<String, Object> getCustomerStatus();

    public Map<String, Object> getCustomerConsume(String month);

}
