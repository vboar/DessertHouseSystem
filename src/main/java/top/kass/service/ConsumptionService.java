package top.kass.service;

import top.kass.model.Consumption;

import java.util.List;

public interface ConsumptionService {

    public List<Consumption> getConsumptionsByCustomer(int id);

}
