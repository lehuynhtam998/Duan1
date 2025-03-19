package com.poly.sms.service.impl;

import com.poly.sms.entity.Product;
import com.poly.sms.entity.ProductImage;
import com.poly.sms.repository.ProductImageRepository;
import com.poly.sms.repository.ProductRepository;
import com.poly.sms.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductImage> findAll() {
        return productImageRepository.findAll();
    }

    @Override
    public Optional<ProductImage> findById(Integer id) {
        return productImageRepository.findById(id);
    }

    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    @Override
    public void deleteById(Integer id) {
        productImageRepository.deleteById(id);
    }

    @Override
    public List<ProductImage> findProImgByPro(Integer id) {
        Product product = productRepository.findByProductId(id);
        return productImageRepository.findByProduct(product);
    }
}
