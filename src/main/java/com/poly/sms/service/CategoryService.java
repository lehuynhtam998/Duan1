package com.poly.sms.service;

import java.util.List;
import java.util.Optional;

import com.poly.sms.dto.CategoryRevenueDTO;
import com.poly.sms.entity.Category;

public interface CategoryService {

    List<Category> findAll();

    Optional<Category> findById(Integer id);

    Category update(Category category);

    void deleteById(Integer id);

    List<CategoryRevenueDTO> getCategoryRevenue();
}
