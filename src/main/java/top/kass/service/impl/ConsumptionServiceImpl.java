package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.ConsumptionDao;
import top.kass.model.Consumption;
import top.kass.service.ConsumptionService;

import java.util.List;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    @Autowired
    private ConsumptionDao consumptionDao;

    @Override
    public List<Consumption> getConsumptionsByCustomer(int id) {
        return consumptionDao.getByCustomerId(id);
    }

}
