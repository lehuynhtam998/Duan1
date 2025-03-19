package com.poly.sms.controller.site;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.sms.entity.Product;
import com.poly.sms.entity.ProductImage;
import com.poly.sms.service.ProductImageService;
import com.poly.sms.service.ProductService;
//Phần code của Tâm
@RestController
@RequestMapping("/api/products")
public class SearchController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/suggestions")
    public List<ProductSuggestionDTO> getSuggestions(@RequestParam("keywords") String keywords) {
        Page<Product> productPage = productService.findByProductNameContaining(keywords);
        List<Product> products = productPage.getContent();
        
        return products.stream().map(product -> {
            List<ProductImage> images = productImageService.findProImgByPro(product.getProductId());
            String imageUrl = !images.isEmpty() ? images.get(0).getUrl() : null;
            return new ProductSuggestionDTO(product.getProductId(), product.getProductName(), imageUrl);
        }).collect(Collectors.toList());
    }

    public static class ProductSuggestionDTO {
        private Integer productId;
        private String productName;
        private String imageUrl;

        public ProductSuggestionDTO(Integer productId, String productName, String imageUrl) {
            this.productId = productId;
            this.productName = productName;
            this.imageUrl = imageUrl;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
//