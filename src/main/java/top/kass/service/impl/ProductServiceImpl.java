package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.kass.dao.ProductDao;
import top.kass.dao.ShopDao;
import top.kass.model.Product;
import top.kass.model.Shop;
import top.kass.service.ProductService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ShopDao shopDao;

    @Override
    public Map<String, Object> add(int shopId, String name, String description,
                                   MultipartFile img) {
        Map<String, Object> map = new HashMap<>();

        name = name.trim();
        description = description.trim();

        if (name.length() == 0|| description.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
            return map;
        }

        if (img == null) {
            map.put("success", false);
            map.put("error", "请上传图片！");
            return map;
        }

        String imgPath = productDao.saveImg(img);

        productDao.create(shopId, name, description, imgPath);
        map.put("success", true);
        return map;
    }

    @Override
    public Map<String, Object> edit(int id, String name, String description, MultipartFile img) {
        Map<String, Object> map = new HashMap<>();
        name = name.trim();
        description = description.trim();

        if (name.length() == 0|| description.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
            return map;
        }

        Product product = productDao.findById(id);

        if (img != null) {
            String imgPath = productDao.saveImg(img);
            product.setImg(imgPath);
        }

        product.setName(name);
        product.setDescription(description);
        product = productDao.update(product);

        map.put("success", true);
        return map;
    }

    @Override
    public Product getProductById(int id) {
        return productDao.findById(id);
    }

    @Override
    public Set<Product> getAllProductsByShop(int shopId) {
        Shop shop = shopDao.findById(shopId);
        return shop.getProducts();
    }

    @Override
    public Map<String, Object> deleteProduct(int id) {
        Map<String, Object> map = new HashMap<>();
        productDao.delete(id);
        map.put("success", true);
        return map;
    }


}
