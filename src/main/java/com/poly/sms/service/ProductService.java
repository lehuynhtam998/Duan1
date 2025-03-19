package com.poly.sms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.poly.sms.entity.Product;

public interface ProductService {

    List<Product> getProductByBranch(Integer branchId);

    List<Product> findAll();

    Optional<Product> findById(Integer id);

    Product update(Product product);

    void deleteById(Integer id);

    List<Product> findTop4ByOrderByDiscountDesc();

    Page<Product> findAllPage(Pageable pageable);

    Page<Product> findProductsByCategoryId(int keywords, Pageable pageable);

    Page<Product> findProductByKeywords(String keywords, Pageable pageable);

    Page<Product> findByProductNameContaining(String productName);

    List<Product> findProductsByCategoryId(int keywords);

     Page<Product> findAllByOrderByUnitPriceDesc(Pageable pageable);

    Page<Product> findAllByOrderByUnitPriceAsc(Pageable pageable);

    Page<Product> findProductsWithDiscountGreaterThanZeroOrderByUnitPriceDesc(Pageable pageable);

    Page<Product> findProductsWithDiscountGreaterThanZeroOrderByUnitPriceAsc(Pageable pageable);

    Page<Product> findByCategoryOrderByUnitPriceDesc(@Param("categoryId") int categoryId, Pageable pageable);

    Page<Product> findByCategoryOrderByUnitPriceAsc(@Param("categoryId") int categoryId, Pageable pageable);

//Phần code của Tâm

    Page<Product> findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceDesc(@Param("categoryId") int categoryId,
            Pageable pageable);

    Page<Product> findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceAsc(@Param("categoryId") int categoryId,
            Pageable pageable);

    // List<Object[]> findExpiringProducts(int days);
    List<Object[]> getTotalCostByMonth(int year);
//
}
