package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import top.kass.model.Product;
import top.kass.model.Shop;
import top.kass.model.User;
import top.kass.service.ProductService;
import top.kass.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    // 产品管理页面
    @RequestMapping(value="/admin/product", method= RequestMethod.GET)
    public ModelAndView productListPage(HttpSession session) {
        int id = (int)session.getAttribute("id");
        User user = userService.getUserById(id);
        Shop shop = user.getShop();
        Set<Product> productSet = shop.getProducts();
        ModelAndView modelAndView = new ModelAndView("admin/product/product_list");
        modelAndView.addObject("shop", shop);
        modelAndView.addObject("productSet", productSet);
        return modelAndView;
    }

    // 新增产品页面
    @RequestMapping(value="/admin/product/add", method= RequestMethod.GET)
    public ModelAndView addProductPage(HttpSession session) {
        int id = (int)session.getAttribute("id");
        User user = userService.getUserById(id);
        Shop shop = user.getShop();
        ModelAndView modelAndView = new ModelAndView("admin/product/add_product");
        modelAndView.addObject("shop", shop);
        return modelAndView;
    }

    // 新增产品操作
    @RequestMapping(value="/admin/product/add", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(String name, String description, MultipartFile img,
                             HttpSession session) {
        int id = (int)session.getAttribute("id");
        int shopId = userService.getUserById(id).getShop().getId();
        Map<String, Object> map = productService.add(shopId, name, description, img);
        return map;
    }

    // 编辑产品页面
    @RequestMapping(value="/admin/product/edit", method= RequestMethod.GET)
    public ModelAndView editProductPage(int id, HttpSession session) {
        int userId = (int)session.getAttribute("id");
        Shop shop = userService.getUserById(userId).getShop();
        Product product = productService.getProductById(id);
        ModelAndView modelAndView = new ModelAndView("admin/product/edit_product");
        modelAndView.addObject("shop", shop);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    // 编辑产品操作
    @RequestMapping(value="/admin/product/edit", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editProduct(int id, String name, String description, MultipartFile img) {
        Map<String, Object> map = productService.edit(id, name, description, img);
        return map;
    }

    // 删除产品操作
    @RequestMapping(value="/admin/product/delete", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProduct(int id) {
        Map<String, Object> map = productService.deleteProduct(id);
        return map;
    }

}
