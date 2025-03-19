package com.poly.sms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.sms.entity.Branch;
import com.poly.sms.entity.Product;
import com.poly.sms.repository.BranchRepository;
import com.poly.sms.repository.ProductRepository;
import com.poly.sms.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findTop4ByOrderByDiscountDesc() {
        return productRepository.findTop4ByOrderByDiscountDesc();
    }

    @Override
    public Page<Product> findAllPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findProductsByCategoryId(int keywords, Pageable pageable) {
        return productRepository.findProductsByCategoryId(keywords, pageable);
    }

    @Override
    public Page<Product> findProductByKeywords(String keywords, Pageable pageable) {
        return productRepository.findProductByKeywords(keywords, pageable);
    }

    @Override
    public Page<Product> findByProductNameContaining(String productName) {
        Pageable pageable = PageRequest.of(0, 5);
        return productRepository.findByProductNameContaining(productName, pageable);
    }

    @Override
    public List<Product> findProductsByCategoryId(int keywords) {
        return productRepository.findProductsByCategoryId(keywords);
    }

    @Override
    public List<Product> getProductByBranch(Integer branchId) {
        Branch branch = branchRepository.findByBranchId(branchId);
        return productRepository.findByBranch(branch);
    }
    @Override
    public Page<Product> findProductsWithDiscountGreaterThanZeroOrderByUnitPriceDesc(Pageable pageable) {
        return productRepository.findProductsWithDiscountGreaterThanZeroOrderByUnitPriceDesc(pageable);
    }

    @Override
    public Page<Product> findProductsWithDiscountGreaterThanZeroOrderByUnitPriceAsc(Pageable pageable) {
        return productRepository.findProductsWithDiscountGreaterThanZeroOrderByUnitPriceAsc(pageable);
    }

    @Override
    public Page<Product> findByCategoryOrderByUnitPriceDesc(int categoryId, Pageable pageable) {
        return productRepository.findByCategoryOrderByUnitPriceDesc(categoryId, pageable);
    }

    @Override
    public Page<Product> findByCategoryOrderByUnitPriceAsc(int categoryId, Pageable pageable) {
        return productRepository.findByCategoryOrderByUnitPriceAsc(categoryId, pageable);
    }

    @Override
    public Page<Product> findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceDesc(int categoryId,
            Pageable pageable) {
        return productRepository.findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceDesc(categoryId,
                pageable);
    }

    @Override
    public Page<Product> findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceAsc(int categoryId,
            Pageable pageable) {
        return productRepository.findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceAsc(categoryId,
                pageable);
    }

    @Override
    public Page<Product> findAllByOrderByUnitPriceDesc(Pageable pageable) {
        return productRepository.findAllByOrderByUnitPriceDesc(pageable);
    }

    @Override
    public Page<Product> findAllByOrderByUnitPriceAsc(Pageable pageable) {
        return productRepository.findAllByOrderByUnitPriceAsc(pageable);
    }

    // @Override
    // public List<Object[]> findExpiringProducts(int days) {
    //     return productRepository.findExpiringProducts(days);
    // }

    @Override
    public List<Object[]> getTotalCostByMonth(int year) {
        return productRepository.getTotalCostByMonth(year);
    }
}
