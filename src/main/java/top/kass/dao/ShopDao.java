package top.kass.dao;

import top.kass.model.Shop;

public interface ShopDao {

    public Shop create(String name, String description, String address);

    public Shop update(Shop shop);

    public Shop findById(int id);

}
