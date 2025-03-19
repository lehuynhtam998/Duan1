package com.poly.sms.controller.app;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.sms.entity.Product;
import com.poly.sms.entity.ProductImage;
import com.poly.sms.service.ProductImageService;
import com.poly.sms.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}/branch")
    public List<Product> getProductByBranch(@PathVariable Integer id) {
        return productService.getProductByBranch(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.update(product);
    }

    @PutMapping("{id}")
    public Product updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            List<ProductImage> images = productImageService.findProImgByPro(id);
            for (ProductImage image : images) {
                productImageService.deleteById(image.getId());
            }

            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("/expiring")
    // public ResponseEntity<List<Object[]>>
    // getExpiringProducts(@RequestParam(defaultValue = "7") int days) {
    // List<Object[]> expiringProducts = productService.findExpiringProducts(days);
    // return ResponseEntity.ok(expiringProducts);
    // }

    @GetMapping("/total-cost-by-month")
    public List<Object[]> getTotalPriceByMonth(@RequestParam int year) {
        return productService.getTotalCostByMonth(year);
    }
}
