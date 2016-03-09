package top.kass.service;

import org.springframework.web.multipart.MultipartFile;
import top.kass.model.Product;
import top.kass.model.Shop;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    public Map<String, Object> add(int shopId, String name, String description, MultipartFile img);

    public Map<String, Object> edit(int id, String name, String description, MultipartFile img);

    public Product getProductById(int id);

    public Set<Product> getAllProductsByShop(int shopId);

    public Map<String, Object> deleteProduct(int id);



}
