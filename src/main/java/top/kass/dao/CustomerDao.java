package top.kass.dao;

import top.kass.model.Customer;

public interface CustomerDao {

    public boolean phoneExist(String phone);

    public Customer create(String phone, String password);

    public Customer findById(int id);

    public Customer findByPhone(String phone);

    public Customer update(Customer customer);

    public Customer findByCode(int code);

}
