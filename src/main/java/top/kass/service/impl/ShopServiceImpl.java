package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kass.dao.ShopDao;
import top.kass.model.Shop;
import top.kass.service.ShopService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public Map<String, Object> add(String name, String description, String address) {
        Map<String, Object> map = new HashMap<>();

        name = name.trim();
        description = description.trim();
        address = address.trim();

        if (name.length() == 0 || description.length() == 0 || address.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
            return map;
        }

        Shop shop = shopDao.create(name, description, address);
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> edit(int id, String name, String description, String address) {

        Map<String, Object> map = new HashMap<>();

        name = name.trim();
        description = description.trim();
        address = address.trim();

        if (name.length() == 0 || description.length() == 0 || address.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
            return map;
        }

        Shop shop = shopDao.findById(id);
        shop.setName(name);
        shop.setDescription(description);
        shop.setAddress(address);
        shop = shopDao.update(shop);

        map.put("success", true);
        return map;
    }

    @Override
    public Shop getShopById(int id) {
        return shopDao.findById(id);
    }

    public List<Shop> getAllShops() {
        return shopDao.getAll();
    }

    @Override
    public Map<String, Object> deleteShop(int id) {
        Map<String, Object> map = new HashMap<>();
        shopDao.delete(id);
        map.put("success", true);
        return map;
    }


}
