package top.kass.dao;

import org.springframework.web.multipart.MultipartFile;
import top.kass.model.Product;

public interface ProductDao {

    public Product create(int shopId, String name, String description, String imgPath);

    public Product update(Product product);

    public Product findById(int id);

    public String saveImg(MultipartFile img);

    public void delete(int id);

}
