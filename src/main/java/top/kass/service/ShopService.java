package top.kass.service;

import top.kass.model.Shop;

import java.util.Map;

public interface ShopService {

    public Map<String, Object> add(String name, String description, String address);

    public Map<String, Object> edit(int id, String name, String description, String address);

    public Shop getShopById(int id);

}
