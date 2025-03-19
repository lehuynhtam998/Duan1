package com.poly.sms.controller.site;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.sms.entity.Product;
import com.poly.sms.entity.ProductImage;
import com.poly.sms.entity.ProductWithImage;
import com.poly.sms.service.CategoryService;
import com.poly.sms.service.OrderDetailService;
import com.poly.sms.service.ProductImageService;
import com.poly.sms.service.ProductService;
import com.poly.sms.service.SiteFeatureService;

@Controller
@RequestMapping("sms")
public class HomeController {

    // @RequestMapping("home")
    // public String home(Model model) {
    //     return "site/index";
    // }

    @Autowired
    SiteFeatureService siteFeatureService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    ProductImageService productImageService;

    @RequestMapping("home")
    public String home(Model model) {

    // danh sách tính năng website
    // List<SiteFeature> features = siteFeatureService.getAllSiteFeatures();

    // danh sách top 4 sản phẩm đặt hàng số lượng nhiều nhất
    List<Product> products = orderDetailService.findTopOrderedProducts()
    .stream()
    .map(result -> (Product) result[0])
    .limit(4)
    .collect(Collectors.toList());

    List<ProductWithImage> productWithImages = getProductWithImages(products);
//Phần code của Tâm

    // danh sách top 6 sản phẩm giảm giá cao nhất
    List<Product> dicounProducts =
    productService.findTop4ByOrderByDiscountDesc();

    List<ProductWithImage> dicountProductWithImages =
    getProductWithImages(dicounProducts);

    model.addAttribute("dicountProductWithImages", dicountProductWithImages);
    model.addAttribute("productWithImages", productWithImages);
    // model.addAttribute("features", features);
    return "site/index";
    }
//
    private List<ProductWithImage> getProductWithImages(List<Product> products) {

    List<ProductImage> productImages = productImageService.findAll()
    .stream()
    .filter(productImage -> products.contains(productImage.getProduct()))
    .collect(Collectors.toList());

    Map<Product, ProductImage> productImageMap = productImages.stream()
    .collect(Collectors.toMap(
    ProductImage::getProduct,
    pi -> pi,
    (existing, replacement) -> existing));

    List<ProductWithImage> productWithImages = products.stream()
    .map(product -> new ProductWithImage(product, productImageMap.get(product)))
    .collect(Collectors.toList());

    return productWithImages;
    }
    

}
