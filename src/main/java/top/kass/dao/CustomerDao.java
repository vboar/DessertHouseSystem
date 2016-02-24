package top.kass.dao;

import top.kass.model.Customer;

public interface CustomerDao {

    public boolean phoneExist(String phone);

    public Customer create(String phone, String password);

}
