package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.kass.service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // 产品管理页面
    @RequestMapping(value="/admin/product", method= RequestMethod.GET)
    public String productListPage() {
        return "admin/product/product_list";
    }

    // 产品详情页面
    @RequestMapping(value="/admin/productDetail", method= RequestMethod.GET)
    public String productDetailPage() {
        return "admin/product/product_detail";
    }

    // 新增产品页面
    @RequestMapping(value="/admin/addProduct", method= RequestMethod.GET)
    public String addProductPage() {
        return "admin/product/add_product";
    }

    // 新增产品操作
    @RequestMapping(value="/admin/addProduct", method= RequestMethod.POST)
    public String addProduct() {
        return "";
    }

    // 编辑产品页面
    @RequestMapping(value="/admin/editProduct", method= RequestMethod.GET)
    public String editProductPage() {
        return "admin/product/edit_product";
    }

    // 编辑产品操作
    @RequestMapping(value="/admin/editProduct", method= RequestMethod.POST)
    public String editProduct() {
        return "";
    }

    // 删除产品操作
    @RequestMapping(value="/admin/deleteProduct", method= RequestMethod.POST)
    public String deleteProduct() {
        return "";
    }

}
