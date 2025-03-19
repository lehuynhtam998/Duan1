package com.poly.sms.controller.app;

import com.poly.sms.entity.ProductImage;
import com.poly.sms.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    public List<ProductImage> getAllProductImages() {
        return productImageService.findAll();
    }

    @GetMapping("/{id}")
    public List<ProductImage> getProductImagesBypro(@PathVariable Integer id) {
        return productImageService.findProImgByPro(id);
    }


    // @GetMapping("/{id}")
    // public ResponseEntity<ProductImage> getProductImageById(@PathVariable Integer id) {
    //     Optional<ProductImage> productImage = productImageService.findById(id);
    //     if (productImage.isPresent()) {
    //         return ResponseEntity.ok(productImage.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PostMapping
    public ProductImage createProductImage(@RequestBody ProductImage productImage) {
        return productImageService.save(productImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImage> updateProductImage(@PathVariable Integer id, @RequestBody ProductImage productImageDetails) {
        Optional<ProductImage> productImage = productImageService.findById(id);
        if (productImage.isPresent()) {
            ProductImage updatedProductImage = productImage.get();
            updatedProductImage.setUrl(productImageDetails.getUrl());
            updatedProductImage.setProduct(productImageDetails.getProduct());
            return ResponseEntity.ok(productImageService.save(updatedProductImage));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Integer id) {
        Optional<ProductImage> productImage = productImageService.findById(id);
        if (productImage.isPresent()) {
            productImageService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
