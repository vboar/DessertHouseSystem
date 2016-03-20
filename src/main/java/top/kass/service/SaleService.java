package top.kass.service;

import java.util.Map;

public interface SaleService {

    public Map<String, Object> payForBook(int id, int type);

    public Map<String, Object> pay(String data, int shopId);

}
