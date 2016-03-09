package top.kass.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.kass.dao.ProductDao;
import top.kass.model.Product;
import top.kass.model.Shop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Product create(int shopId, String name, String description, String imgPath) {

        Session session = sessionFactory.getCurrentSession();

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setImg(imgPath);
        Shop tempShop = new Shop();
        tempShop.setId(shopId);
        product.setShop(tempShop);
        session.save(product);

        return product;
    }

    @Override
    public Product update(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
        session.flush();
        return product;
    }

    @Override
    public Product findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Product where id=:id");
        query.setInteger("id", id);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Product) query.list().get(0);
        }
    }

    @Override
    public String saveImg(MultipartFile img) {

        String dir = "D:/Project/DessertHouseSystem/target/dessert-house-system-1.0-SNAPSHOT";
        String subDir = "/assets/resource/img/products/";
        String originalName = img.getOriginalFilename();
        long l = System.currentTimeMillis();
        String filename = String.valueOf(l) + originalName;

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(dir + subDir + filename));
            int byteCount = 0;
            byte[] bytes = new byte[1024];
            InputStream stream = img.getInputStream();
            while ((byteCount = stream.read(bytes)) != -1){
                outputStream.write(bytes, 0, byteCount);
            }
            outputStream.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subDir + filename;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete Product where id=:id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

}
