package com.poly.sms.controller.site;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.sms.entity.Product;
import com.poly.sms.entity.ProductImage;
import com.poly.sms.entity.ProductWithImage;
import com.poly.sms.service.CategoryService;
import com.poly.sms.service.OrderDetailService;
import com.poly.sms.service.ProductImageService;
import com.poly.sms.service.ProductService;

@Controller
@RequestMapping("sms")
public class ShopDetailController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderDetailService orderDetailService;

    @RequestMapping("shopdetails/{id}")
    public String shopDetail(Model model, @PathVariable("id") int id) {
        Product product = productService.findById(id).orElse(new Product());

        List<ProductImage> productImages = productImageService.findProImgByPro(id);

        List<Product> products = productService.findProductsByCategoryId(product.getCategory().getCategoryId());

        List<ProductWithImage> productWithImages = getProductWithImages(products);

        ProductWithImage selectedProductWithImage = productWithImages.stream()
                .filter(pwi -> pwi.getProduct().getProductId() == id)
                .findFirst()
                .orElse(null);

        // danh sách top 6 sản phẩm giảm giá cao nhất
        List<Product> dicounProducts = productService.findTop4ByOrderByDiscountDesc();

        List<ProductWithImage> dicountProductWithImages = getProductWithImages(dicounProducts);

        model.addAttribute("selectedProductWithImage", selectedProductWithImage);
        model.addAttribute("productImages", productImages);
        model.addAttribute("productWithImages", productWithImages);
        model.addAttribute("dicountProductWithImages", dicountProductWithImages);
        return "site/shop-detail";
    }

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
