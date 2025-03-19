package com.poly.sms.service;

import com.poly.sms.entity.ProductImage;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {

    List<ProductImage> findAll();

    List<ProductImage> findProImgByPro(Integer id);

    Optional<ProductImage> findById(Integer id);

    ProductImage save(ProductImage productImage);

    void deleteById(Integer id);
}
