package com.poly.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.sms.dto.CategoryRevenueDTO;
import com.poly.sms.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT new com.poly.sms.dto.CategoryRevenueDTO(c.name, SUM(od.quantity * od.price)) FROM OrderDetail od JOIN od.product p JOIN p.category c GROUP BY c.name")
    List<CategoryRevenueDTO> getCategoryRevenue();
}
