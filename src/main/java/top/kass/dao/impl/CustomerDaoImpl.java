package top.kass.dao.impl;

import org.springframework.stereotype.Repository;
import top.kass.dao.CustomerDao;
import top.kass.model.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    public boolean phoneExist(String phone) {
        return true;
    }

    @Override
    public Customer create(String phone, String password) {

        return null;
    }


}
