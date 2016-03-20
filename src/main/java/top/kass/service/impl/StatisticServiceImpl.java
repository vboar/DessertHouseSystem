package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.StatisticDao;
import top.kass.service.StatisticService;

import java.util.Map;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticDao statisticDao;

    @Override
    public Map<String, Object> getCustomerAge() {
        return statisticDao.getCustomerAge();
    }

    @Override
    public Map<String, Object> getCustomerGender() {
        return statisticDao.getCustomerGender();
    }

    @Override
    public Map<String, Object> getCustomerCity() {
        return statisticDao.getCustomerCity();
    }

    @Override
    public Map<String, Object> getCustomerStatus() {
        return statisticDao.getCustomerStatus();
    }

    @Override
    public Map<String, Object> getCustomerConsume(String month) {
        return statisticDao.getCustomerConsume(month);
    }

    @Override
    public Map<String, Object> getProducts(String month) {
        return statisticDao.getProducts(month);
    }

    @Override
    public Map<String, Object> getSales(String month) {
        return statisticDao.getSales(month);
    }

}
