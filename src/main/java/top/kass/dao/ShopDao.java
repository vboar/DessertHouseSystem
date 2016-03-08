package top.kass.dao;

import top.kass.model.Shop;

import java.util.List;

public interface ShopDao {

    public Shop create(String name, String description, String address);

    public Shop update(Shop shop);

    public Shop findById(int id);

    public List<Shop> getAll();

    public void delete(int id);

}
