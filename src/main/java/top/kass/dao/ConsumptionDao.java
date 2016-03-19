package top.kass.dao;

import top.kass.model.Consumption;

import java.util.List;

public interface ConsumptionDao {

    public Consumption createOrUpdate(Consumption consumption);

    public List<Consumption> getByCustomerId(int id);

}
